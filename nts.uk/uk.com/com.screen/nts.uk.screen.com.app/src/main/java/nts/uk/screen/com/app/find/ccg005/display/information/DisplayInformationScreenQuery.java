package nts.uk.screen.com.app.find.ccg005.display.information;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.office.dom.favorite.adapter.EmployeeBasicImport;
import nts.uk.ctx.office.dom.favorite.adapter.EmployeeBelongWorkplaceAdapter;
import nts.uk.ctx.office.dom.favorite.adapter.EmployeePositionAdapter;
import nts.uk.ctx.office.dom.favorite.adapter.EmployeeWorkplaceIdAdapter;
import nts.uk.ctx.office.dom.favorite.adapter.PersonalInformationAdapter;
import nts.uk.ctx.office.dom.favorite.adapter.RankOfPositionAdapter;
import nts.uk.ctx.office.dom.favorite.adapter.WorkplaceInforAdapter;
import nts.uk.ctx.office.dom.favorite.service.DefaultEmpFromWpDSRequireImpl;
import nts.uk.ctx.office.dom.favorite.service.DefaultPersonalInfomationRequireImpl;
import nts.uk.ctx.office.dom.favorite.service.EmployeeListFromWpDomainService;
import nts.uk.ctx.office.dom.favorite.service.PersonalInfomationDomainService;
import nts.uk.ctx.office.dom.reference.auth.SpecifyAuthInquiryRepository;
import nts.uk.ctx.office.dom.reference.auth.service.DefaultRequireImpl;
import nts.uk.ctx.office.dom.reference.auth.service.DetermineEmpIdListDomainService;
import nts.uk.screen.com.app.find.ccg005.attendance.information.AttendanceInformationDto;
import nts.uk.screen.com.app.find.ccg005.attendance.information.AttendanceInformationScreenQuery;
import nts.uk.screen.com.app.find.ccg005.attendance.information.EmpIdParam;
import nts.uk.shr.com.context.AppContexts;

/*
 * UKDesign.UniversalK.共通.CCG_メニュートップページ.CCG005_ミニ在席照会.A:ミニ在席照会.メニュー別OCD.選択した後の表示情報を取得
 */
@Stateless
public class DisplayInformationScreenQuery {

	@Inject
	private EmployeeWorkplaceIdAdapter empWkspAdapter;

	@Inject
	private EmployeeBelongWorkplaceAdapter empBelongWkspAdapter;
	
	@Inject
	private SpecifyAuthInquiryRepository specAuthRepo;
	
	@Inject
	private EmployeePositionAdapter emplPosAdapter;
	
	@Inject
	private EmployeeWorkplaceIdAdapter employeeWorkplaceIdAdapter;
	
	@Inject
	private WorkplaceInforAdapter workplaceInforAdapter;
	
	@Inject
	private EmployeePositionAdapter employeePositionAdapter;
	
	@Inject
	private RankOfPositionAdapter rankOfPositionAdapter;
	
	@Inject
	private PersonalInformationAdapter personalInformationAdapter;

	public DisplayInformationDto getDisplayInfoAfterSelect(List<String> wkspIds, GeneralDate baseDate, boolean emojiUsage) {
		String loginCid = AppContexts.user().companyId();
		String loginRoleid = AppContexts.user().roles().forAttendance();
		String loginSid = AppContexts.user().employeeId();

		// 1: 社員IDリストを取得する(Require, 職場ID, 年月日): List<社員ID>
		DefaultEmpFromWpDSRequireImpl empLstRequireImpl = new DefaultEmpFromWpDSRequireImpl(empWkspAdapter, empBelongWkspAdapter);
		List<String> getEmployeeIdList = EmployeeListFromWpDomainService.getEmployeeIdList(empLstRequireImpl, loginSid, wkspIds, baseDate);
		
		// 2: 参照できるか判断する(Require, 社員ID, 年月日): List<社員ID>
		DefaultRequireImpl deterEmpLstDsRequireImpl = new DefaultRequireImpl(specAuthRepo, emplPosAdapter);
		List<String> getDeterEmployeeIdList = DetermineEmpIdListDomainService.determineReferenced(deterEmpLstDsRequireImpl, getEmployeeIdList, baseDate, loginCid, loginRoleid, loginSid);
		
		// 3: 個人情報を取得(Require, 社員ID, 年月日): List<個人基本情報>
		DefaultPersonalInfomationRequireImpl rq = new DefaultPersonalInfomationRequireImpl(
				employeeWorkplaceIdAdapter,
				workplaceInforAdapter,
				employeePositionAdapter,
				rankOfPositionAdapter,
				personalInformationAdapter);
		List<EmployeeBasicImport> listPersonalInfo = PersonalInfomationDomainService.getPersonalInfomation(rq, getDeterEmployeeIdList, baseDate);
		
		return DisplayInformationDto.builder()
				.listPersonalInfo(listPersonalInfo)
				.build();
	}
}
