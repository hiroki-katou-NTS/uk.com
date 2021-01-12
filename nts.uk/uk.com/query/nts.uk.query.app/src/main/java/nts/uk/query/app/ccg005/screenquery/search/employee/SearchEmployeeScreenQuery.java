package nts.uk.query.app.ccg005.screenquery.search.employee;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.office.dom.favorite.adapter.EmployeePositionAdapter;
import nts.uk.ctx.office.dom.reference.auth.SpecifyAuthInquiryRepository;
import nts.uk.ctx.office.dom.reference.auth.service.DefaultRequireImpl;
import nts.uk.ctx.office.dom.reference.auth.service.DetermineEmpIdListDomainService;
import nts.uk.query.app.employee.RegulationInfoEmployeeFinder;
/*
 * UKDesign.UniversalK.共通.CCG_メニュートップページ.CCG005_ミニ在席照会.A:ミニ在席照会.メニュー別OCD.社員を検索する.社員を検索する
 */
public class SearchEmployeeScreenQuery {
	
	@Inject
	private RegulationInfoEmployeeFinder employeeFinder;
	
	private SpecifyAuthInquiryRepository repo;

	private EmployeePositionAdapter adapter;
	public void searchForEmployee(String keyWorks, GeneralDate baseDate, boolean emojiUseage) {
		List<String> sidsSearch = new ArrayList<>();
		//1: 社員コードで検索する() 
		List<String> sidsByCode = employeeFinder.searchByEmployeeCode(keyWorks, 0, baseDate); // システム区分　＝　就業  ＝  0
		sidsSearch.addAll(sidsByCode);
		//2: 社員名で検索する()
		List<String> sidsByName = employeeFinder.searchByEmployeeName(keyWorks, 0, baseDate);  // システム区分　＝　就業  ＝  0
		sidsSearch.addAll(sidsByName);
		//3: 参照できるか判断する(Require, 社員ID, 年月日): List<社員ID>
		DefaultRequireImpl deterEmpLstDsRequireImpl = new DefaultRequireImpl(repo, adapter);
		List<String> empList = DetermineEmpIdListDomainService.determineReferenced(deterEmpLstDsRequireImpl, sidsSearch, baseDate);
		//TODO 4: 個人情報を取得(Require, 社員ID, 年月日): List<個人基本情報>
		//TODO 5: 在席情報を取得する(社員ID, 年月日, するしない区分): List<在席情報DTO>
	}
}
