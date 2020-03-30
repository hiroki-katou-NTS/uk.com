/**
 * 
 */
package nts.uk.ctx.hr.shared.dom.personalinfo.laborcontracthistory.algorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.shared.dom.personalinfo.laborcontracthistory.EmployeeInfoDto;
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
	public List<LaborContractHistoryDto> getHistoryByListSidAndStartDate(String cid, List<String> sids, GeneralDate startDate, Optional<Integer> autoCreateHisOfNoContractPeriod){
		
		// ドメイン [労働契約履歴] を取得する(Lấy domain [LaborContractHistory])
		List<LaborContractHistoryDto> listLaborContractHistory =  laborContractHisRepo.getListDomainByListSidAndStartDate(cid, sids, startDate);
		if (listLaborContractHistory.isEmpty()) {
			return new ArrayList<LaborContractHistoryDto>();
		}
		
		Map<String, List<LaborContractHistoryDto>> mapSidAndListDomain =  listLaborContractHistory.stream().collect(Collectors.groupingBy(LaborContractHistoryDto::getSid));
		
		Map<String, List<LaborContractHistoryDto>> mapSidAndListDomainSorted = new HashMap<String, List<LaborContractHistoryDto>>();
		
		// 取得した労働契約履歴を社員ID単位に分類して履歴を期間開始日の降順でソートする(Phân loại LaborContractHistory theo đơn vị EmployeeID, )
		mapSidAndListDomain.forEach((k, v) -> {
			String sid = k;
			List<LaborContractHistoryDto> listDomain = v;
			List<LaborContractHistoryDto> sortedList = new ArrayList<>();
			if (!listDomain.isEmpty()) {
				sortedList = listDomain.stream()
						.sorted(Comparator.comparing(LaborContractHistoryDto::getStartDate).reversed())
						.collect(Collectors.toList());
			}
			
			mapSidAndListDomainSorted.put(sid, sortedList);

		});
		
		if (autoCreateHisOfNoContractPeriod.isPresent()) {
			if (autoCreateHisOfNoContractPeriod.get() == 1) {
				
				// 新規リストを作成(tạo list mới)
				List<LaborContractHistoryDto> newList = new ArrayList<>();
				
				// 社員ID単位に処理(xử lý thành đơn vị EmployeeID)
				//次の処理対象社員IDの有無チェック(check có hoặc không có employeeID đối tượng xử lý tiếp theo)
				mapSidAndListDomainSorted.forEach((key, v) -> {
					List<LaborContractHistoryDto> listSorted = v;
					if (listSorted.size() > 1) {
						for (int i = 0 ; i < listSorted.size() - 1 ; i++) {
							LaborContractHistoryDto history = listSorted.get(i);
							LaborContractHistoryDto historyNext = listSorted.get( i+ 1);
							LaborContractHistoryDto currentHis = LaborContractHistoryDto.builder()
									.cid(history.getCid())
									.sid(history.getSid())
									.hisId(history.getHisId())
									.startDate(history.getStartDate())
									.endDate(history.getEndDate())
									.contractStatus(1).build();
							newList.add(currentHis);
							
							if (!history.getStartDate().equals(historyNext.getEndDate())) {
								LaborContractHistoryDto newHis = LaborContractHistoryDto.builder()
										.cid(history.getCid())
										.sid(history.getSid())
										.hisId(null)
										.startDate(historyNext.getStartDate())
										.endDate(history.getStartDate())
										.contractStatus(0).build();
								newList.add(newHis);
							}
						}
					} else if(listSorted.size() == 1){
						LaborContractHistoryDto currentHis = LaborContractHistoryDto.builder()
								.cid(listSorted.get(0).getCid())
								.sid(listSorted.get(0).getSid())
								.hisId(listSorted.get(0).getHisId())
								.startDate(listSorted.get(0).getStartDate())
								.endDate(listSorted.get(0).getEndDate())
								.contractStatus(1).build();
						newList.add(currentHis);
					}
				});
				
				return newList;
				
				
			}else if(autoCreateHisOfNoContractPeriod.get() == 0){
				
				return listLaborContractHistory;
			}
			
		}else{
			
			return listLaborContractHistory;
		}
		
		return new ArrayList<>();
		
	}
}
