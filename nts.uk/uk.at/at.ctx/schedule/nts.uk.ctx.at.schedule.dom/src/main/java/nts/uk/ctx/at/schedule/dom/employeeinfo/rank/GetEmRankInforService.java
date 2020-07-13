package nts.uk.ctx.at.schedule.dom.employeeinfo.rank;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 社員ランク情報を取得する
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務予定.社員情報.ランク
 * @author HieuLt
 *
 */
public class GetEmRankInforService {
	
	
	public static List<EmpRankInfor> get(Require require , List<String> listEmpId	){
	
		
		//$社員ランクMap = require.社員ランクを取得する(社員リスト)	
		Map<String, List<EmployeeRank>> mapEmployeeRank = require.getAll(listEmpId).stream().collect(Collectors.groupingBy(EmployeeRank :: getSID ));
		
		Map<String, List<Rank>> mapRankBycode = require.getListRank().stream().collect(Collectors.groupingBy(c -> c.getRankCode().toString()));
	
		List<EmpRankInfor> result =  listEmpId.stream().map(mapper -> {
			// 	$社員ランク = $社員ランクMap.get($)
			List<EmployeeRank> listEmployeeRank = mapEmployeeRank.get(mapper);
			//if $社員ランク.empty				
			if (listEmployeeRank.isEmpty()) {
				//	return 社員ランク情報.ランクなしで作る($)	
				return new EmpRankInfor(mapper);
			}
			//$ランク = $ランクMap.get($社員ランク.ランクコード)												
			List<String> lstRankCode = listEmployeeRank.stream().map(i -> i.getEmplRankCode().toString()).collect(Collectors.toList());
			List<Rank> lstRank = mapRankBycode.get(lstRankCode);
			
			if (lstRank.isEmpty()) {
				//社員ランク情報.ランクなしで作る($)
				return new EmpRankInfor(mapper);
			}
			//	return 社員ランク情報.作る($, ＄ランク.ランクコード, $ランク.ランク記号)
			return new EmpRankInfor(mapper, lstRank.get(0).getRankCode().toString() , lstRank.get(0).getRankSymbol().toString());
			
		}).collect(Collectors.toList());
		
		return result;
	
	}

	
	public static interface Require{
		/**
		 * EmployeeRankRepository
		 * [R1] 社員ランクを取得する
		 * @param lstSID
		 * @return
		 */
		List<EmployeeRank> getAll(List<String> lstSID);
		
		/**
		 * RankRepository
		 * [R2] 会社のランクを取得する
		 * @param companyId
		 * @return
		 */
		List<Rank> getListRank();
		
	}
}
