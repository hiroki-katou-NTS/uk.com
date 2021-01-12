package nts.uk.query.app.ccg005.screenquery.display.information;

import java.util.List;

import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.office.dom.favorite.adapter.EmployeeBelongWorkplaceAdapter;
import nts.uk.ctx.office.dom.favorite.adapter.EmployeePositionAdapter;
import nts.uk.ctx.office.dom.favorite.adapter.EmployeeWorkplaceIdAdapter;
import nts.uk.ctx.office.dom.favorite.service.DefaultEmpFromWpDSRequireImpl;
import nts.uk.ctx.office.dom.favorite.service.EmployeeListFromWpDomainService;
import nts.uk.ctx.office.dom.favorite.service.PersonalInfomationDomainService;
import nts.uk.ctx.office.dom.reference.auth.SpecifyAuthInquiryRepository;
import nts.uk.ctx.office.dom.reference.auth.service.DefaultRequireImpl;
import nts.uk.ctx.office.dom.reference.auth.service.DetermineEmpIdListDomainService;
import nts.uk.shr.com.context.AppContexts;

/*
 * UKDesign.UniversalK.共通.CCG_メニュートップページ.CCG005_ミニ在席照会.A:ミニ在席照会.メニュー別OCD.選択した後の表示情報を取得
 */
public class DisplayInformationScreenQuery {

	private EmployeeListFromWpDomainService empLstDs;

	@Inject
	private EmployeeWorkplaceIdAdapter empWkspAdapter;

	@Inject
	private EmployeeBelongWorkplaceAdapter empBelongWkspAdapter;

	private DetermineEmpIdListDomainService DeterEmpLstDs;
	
	@Inject
	private SpecifyAuthInquiryRepository specAuthRepo;
	
	@Inject
	private EmployeePositionAdapter emplPosAdapter;

	private PersonalInfomationDomainService personalInfoDs;

	//TODO khong biet output la gi
	private void getDisplayInfoAfterSelect(List<String> wkspIds, GeneralDate baseDate, boolean emojiUseage) {
		// 1: 社員IDリストを取得する(Require, 職場ID, 年月日): List<社員ID>
		String loginSid = AppContexts.user().employeeId();
		DefaultEmpFromWpDSRequireImpl empLstRequireImpl = new DefaultEmpFromWpDSRequireImpl(empWkspAdapter, empBelongWkspAdapter);
		List<String> getEmployeeIdList = empLstDs.getEmployeeIdList(empLstRequireImpl, loginSid, wkspIds, baseDate);
		// 2: 参照できるか判断する(Require, 社員ID, 年月日): List<社員ID>
		DefaultRequireImpl deterEmpLstDsRequireImpl = new DefaultRequireImpl(specAuthRepo, emplPosAdapter);
		List<String> getDeterEmployeeIdList = DeterEmpLstDs.determineReferenced(deterEmpLstDsRequireImpl, getEmployeeIdList, baseDate);
		// TODO 3: 個人情報を取得(Require, 社員ID, 年月日): List<個人基本情報>
		
		// TODO: 4:在席情報を取得する(社員ID, 年月日, するしない区分): List<在席情報DTO>
	}

}
