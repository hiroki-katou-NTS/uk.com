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
		List<EmployeeRank> lstEmpRank = require.getAll(listEmpId);
		Map<String, EmployeeRank> mapEmployeeRank = lstEmpRank.stream().collect(Collectors.toMap(EmployeeRank::getSID, x->x));
		
		List<Rank> lstRank = require.getListRank();
		Map<String, Rank> mapRankBycode = lstRank.stream().collect(Collectors.toMap(x-> x.getRankCode().v(), x-> x));
	
		List<EmpRankInfor> result =  listEmpId.stream().map(mapper -> {
			// 	$社員ランク = $社員ランクMap.get($)
			EmployeeRank employeeRank = mapEmployeeRank.get(mapper);
			//if $社員ランク.empty				
			if (employeeRank == null) {
				//	return 社員ランク情報.ランクなしで作る($)	
				return EmpRankInfor.makeWithoutRank(mapper);
			}
			//$ランク = $ランクMap.get($社員ランク.ランクコード)												
			Rank rank = mapRankBycode.get(employeeRank.getEmplRankCode().v());
			
			if (rank == null) {
				//社員ランク情報.ランクなしで作る($)
				return EmpRankInfor.makeWithoutRank(mapper);
			}
			//	return 社員ランク情報.作る($, ＄ランク.ランクコード, $ランク.ランク記号)
			return EmpRankInfor.create(mapper, new RankCode(rank.getRankCode().v()), new RankSymbol(rank.getRankSymbol().v()));
			
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
