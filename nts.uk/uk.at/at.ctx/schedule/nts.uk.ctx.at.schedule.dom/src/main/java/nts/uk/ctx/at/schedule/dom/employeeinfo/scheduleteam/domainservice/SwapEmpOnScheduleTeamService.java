package nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.domainservice;

import java.util.ArrayList;
import java.util.List;

import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.BelongScheduleTeam;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeam;

/**
 * スケジュールチームに所属する社員を入れ替える
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務予定.社員情報.スケジュールチーム
 * 
 * @author HieuLt
 *
 */

public class SwapEmpOnScheduleTeamService {

	/**
	 * [1] 入れ替える
	 * 
	 * @param require
	 * @param scheduleTeam
	 * @param lstEmpID
	 * @return
	 */

	public static AtomTask replace(Require require, ScheduleTeam scheduleTeam, List<String> lstEmpID) {
		//map チーム.所属する社員を追加する( $ )				
		List<BelongScheduleTeam> data = new ArrayList<>();
		for(String item : lstEmpID){
			BelongScheduleTeam belong = scheduleTeam.addEmployee(item);
			data.add(belong);
		}	
		return AtomTask.of(() -> {
			// require.チームを指定して所属スケジュールチームを削除する( チーム.職場グループID, チーム.コード )
			require.deleteSpecifyTeamAndScheduleTeam(scheduleTeam.getWKPGRPID(), scheduleTeam.getScheduleTeamCd().v());
			// $登録対象リスト: for
			data.forEach(x -> {
				if (require.empBelongTeam(x.getEmployeeID())) {
					require.delete(x.getEmployeeID());
				}
				require.insert(x);
			});
		});	
	}

	public static interface Require {
		/**
		 * 	[R-1] チームを指定して所属スケジュールチームを削除する		
		 * 所属スケジュールチームRepository.delete( 会社ID, 職場グループID, スケジュールチームコード )			
		 * @param companyID
		 * @param WKPGRPID
		 * @param scheduleTeamCd
		 * @return
		 */
		void deleteSpecifyTeamAndScheduleTeam(String WKPGRPID, String scheduleTeamCd);

		/**
		 * [R-2] 社員が既にチームに所属しているか
		 * 
		 * @param companyID
		 * @param empID
		 * @return
		 */
		boolean empBelongTeam(String empID);

		/**
		 * [R-3] 社員を指定して所属スケジュールチームを削除する
		 * 
		 * @param companyID
		 * @param empID
		 */
		void delete(String empID);

		/**
		 * [R-4] 所属スケジュールチームを追加する
		 * 
		 * @param belongScheduleTeam
		 */
		void insert(BelongScheduleTeam belongScheduleTeam);

	}
}
