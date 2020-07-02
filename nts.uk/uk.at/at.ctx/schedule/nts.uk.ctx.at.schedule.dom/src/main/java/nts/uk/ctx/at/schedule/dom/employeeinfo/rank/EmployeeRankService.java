package nts.uk.ctx.at.schedule.dom.employeeinfo.rank;

import nts.arc.task.tran.AtomTask;
/**
 * 社員ランクを登録する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.社員情報.ランク.社員ランクを登録する.社員ランクを登録する
 * @author phongtq
 *
 */
public class EmployeeRankService {
	/**
	 * 	[1] 登録する
	 * @param require
	 * @param sID
	 * @param emplRankCode
	 * @return
	 */
	public static AtomTask insert(Require require, String sID, RankCode emplRankCode) {
		
		// $社員ランク = 社員ランク(社員ID, ランクコード)
		EmployeeRank employeeRank = new EmployeeRank(sID, emplRankCode);
		
		// if require.指定の社員ランクが存在するか(社員ID)												
		if(require.exists(sID) == true)
			//return AtomTask:　require.社員ランクを変更する($社員ランク）
			return AtomTask.of(() -> {
				require.update(employeeRank);
			});
		
		// return AtomTask:　require.社員ランクを追加する($社員ランク)	
		return AtomTask.of(() -> {
			require.insert(employeeRank);
		});
	}
	
	public static interface Require{
		
		// [R1] 指定の社員ランクが存在するか
		// 社員ランクRepository.exists(社員ID）				
		boolean exists(String sID);
		
		// [R2] 社員ランクを追加する
		// 社員ランクRepository.insert(社員ランク）
		public void insert(EmployeeRank employeeRank);
		
		// [R3] 社員ランクを変更する
		// 社員ランクRepository.update(社員ランク）
		public void update(EmployeeRank employeeRank);
	}
}
