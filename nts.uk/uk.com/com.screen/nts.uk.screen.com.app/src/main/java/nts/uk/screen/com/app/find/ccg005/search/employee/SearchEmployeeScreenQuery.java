package nts.uk.screen.com.app.find.ccg005.search.employee;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.office.dom.favorite.adapter.EmployeeBasicImport;
import nts.uk.ctx.office.dom.favorite.adapter.EmployeePositionAdapter;
import nts.uk.ctx.office.dom.favorite.adapter.EmployeeWorkplaceIdAdapter;
import nts.uk.ctx.office.dom.favorite.adapter.PersonalInformationAdapter;
import nts.uk.ctx.office.dom.favorite.adapter.RankOfPositionAdapter;
import nts.uk.ctx.office.dom.favorite.adapter.WorkplaceInforAdapter;
import nts.uk.ctx.office.dom.favorite.service.DefaultPersonalInfomationRequireImpl;
import nts.uk.ctx.office.dom.favorite.service.PersonalInfomationDomainService;
import nts.uk.ctx.office.dom.reference.auth.SpecifyAuthInquiryRepository;
import nts.uk.ctx.office.dom.reference.auth.service.DefaultRequireImpl;
import nts.uk.ctx.office.dom.reference.auth.service.DetermineEmpIdListDomainService;
import nts.uk.query.pub.employee.SearchEmployeePub;
import nts.uk.shr.com.context.AppContexts;

/*
 * UKDesign.UniversalK.共通.CCG_メニュートップページ.CCG005_ミニ在席照会.A:ミニ在席照会.メニュー別OCD.社員を検索する.社員を検索する
 */
@Stateless
public class SearchEmployeeScreenQuery {

	@Inject
	private SearchEmployeePub searchEmployeePub;

	@Inject
	private SpecifyAuthInquiryRepository specifyAuthInquiryRepository;

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
	
	public SearchEmployeeDto searchForEmployee(String keyWorks, GeneralDate baseDate, boolean emojiUsage) {

		String loginCid = AppContexts.user().companyId();
		String loginRoleid = AppContexts.user().roles().forAttendance();
		String loginSid = AppContexts.user().employeeId();

		List<String> sidsSearch = new ArrayList<>();

		// 1: 社員コードで検索する()
		List<String> sidsByCode = searchEmployeePub.searchByEmployeeCode(keyWorks, 2, baseDate); // システム区分 ＝ 就業 ＝ 2
		sidsSearch.addAll(sidsByCode);

		// 2: 社員名で検索する()
		List<String> sidsByName = searchEmployeePub.searchByEmployeeName(keyWorks, 2, baseDate); // システム区分 ＝ 就業 ＝ 2
		sidsSearch.addAll(sidsByName);

		List<String> distinctSidsSearch = sidsSearch.stream()
                .distinct()
                .collect(Collectors.toList());
		
		// 3: 参照できるか判断する(Require, 社員ID, 年月日): List<社員ID>
		DefaultRequireImpl deterEmpLstDsRequireImpl = 
				new DefaultRequireImpl(specifyAuthInquiryRepository, employeePositionAdapter);
		List<String> empList = DetermineEmpIdListDomainService
				.determineReferenced(
						deterEmpLstDsRequireImpl, 
						distinctSidsSearch,
						baseDate, 
						loginCid, 
						loginRoleid, 
						loginSid);

		// 4: 個人情報を取得(Require, 社員ID, 年月日): List<個人基本情報>
		DefaultPersonalInfomationRequireImpl defaultPersonalInfomationRequireImpl = 
				new DefaultPersonalInfomationRequireImpl(
					employeeWorkplaceIdAdapter, 
					workplaceInforAdapter, 
					employeePositionAdapter, 
					rankOfPositionAdapter,
					personalInformationAdapter);
		List<EmployeeBasicImport> personalInfomation = PersonalInfomationDomainService
				.getPersonalInfomation(defaultPersonalInfomationRequireImpl, empList, baseDate);

		return SearchEmployeeDto.builder()
				.listPersonalInfo(personalInfomation)
				.build();
	}
}
