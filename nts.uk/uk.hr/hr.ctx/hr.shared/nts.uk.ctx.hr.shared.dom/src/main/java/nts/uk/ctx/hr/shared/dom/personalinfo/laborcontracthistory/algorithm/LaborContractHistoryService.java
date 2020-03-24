/**
 * 
 */
package nts.uk.ctx.hr.shared.dom.personalinfo.laborcontracthistory.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.shared.dom.personalinfo.laborcontracthistory.EmployeeInfoDto;
import nts.uk.ctx.hr.shared.dom.personalinfo.laborcontracthistory.LaborContractHistory;
import nts.uk.ctx.hr.shared.dom.personalinfo.laborcontracthistory.LaborContractHistoryDto;
import nts.uk.ctx.hr.shared.dom.personalinfo.laborcontracthistory.WageTypeDto;

/**
 * @author laitv
 *
 */

@Stateless
public class LaborContractHistoryService {
	
	@Inject
	private LaborContractHistoryRepository laborContractHisRepo;
	
	
	// 基準日、社員IDリストより、給与区分を取得する
	// path :UKDesign.ドメインモデル.NittsuSystem.UniversalK.人事.shared.個人情報（人事）.労働契約履歴.アルゴリズム.基準日、社員IDリストより、給与区分を取得する
	// [Input]
	//・基準日// baseDate
	//・List<社員ID>// List<EmployeeID>
	public List<WageTypeDto> getWageTypeInfo(List<String> sids, GeneralDate baseDate){
		 
		List<WageTypeDto> result = laborContractHisRepo.getListWageType(sids, baseDate);
		 return result;
	}

	// 労働契約の満了日を迎える社員を抽出する
	// path :UKDesign.ドメインモデル.NittsuSystem.UniversalK.人事.shared.個人情報（人事）.労働契約履歴.アルゴリズム.基準日、社員IDリストより、給与区分を取得する
	// [Input]
	//・会社ID// companyID
	//・期間開始日// PeriodStartDate
	//・期間終了日// PeriodEndDate
	public List<EmployeeInfoDto> getEmployeeHasEmploymentContractExpire(String cid, GeneralDate startDate, GeneralDate endDate) {
		
		List<EmployeeInfoDto> result = laborContractHisRepo.getListEmployeeInfoDto(cid, startDate, endDate);
		return result;

	}
	
	
	// 社員IDリスト、期間開始日を指定して履歴を取得する
	// path:UKDesign.ドメインモデル.NittsuSystem.UniversalK.人事.shared.個人情報（人事）.労働契約履歴.アルゴリズム.発注対象外.社員IDリスト、期間開始日を指定して履歴を取得する
	// [Input]
	//・会社ID
	//・list<社員ID>
	//・期間開始日
	//・(optional)int 無契約期間の履歴を自動作成する：　1：自動作成する　０：自動作成しない
	//default：自動作成する
	public List<LaborContractHistory> getHistoryByListSidAndStartDate(String cid, List<String> sids, GeneralDate startDate, Optional<Integer> autoCreateHisOfNoContractPeriod){
		
		// ドメイン [労働契約履歴] を取得する(Lấy domain [LaborContractHistory])
		List<LaborContractHistoryDto> listLaborContractHistory =  laborContractHisRepo.getListDomainByListSidAndStartDate(cid, sids, startDate);
		if (listLaborContractHistory.isEmpty()) {
			return new ArrayList<LaborContractHistory>();
		}
		
		Map<String, List<LaborContractHistoryDto>> mapSidAndListDomain =  listLaborContractHistory.stream().collect(Collectors.groupingBy(LaborContractHistoryDto::getSid));
		    
		for (Map.Entry me : mapSidAndListDomain.entrySet()) {
			String key = me.getKey().toString();
			List<LaborContractHistory> values = (List<LaborContractHistory>) me.getValue();
			for (int i = 0; i < values.size(); i++) {

				
				
				
				
			}
		}
		
		return new ArrayList<>();
		
		
		
		
	}
}
