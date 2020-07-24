package nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.domainservice;

import java.util.List;
import java.util.stream.Collectors;

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
		// $登録対象リスト = 社員IDリスト: map 所属スケジュールチーム#作る( $, チーム.職場グループID, チーム.コード )
		List<BelongScheduleTeam> data = lstEmpID.stream()
				.map(c -> new BelongScheduleTeam(c, scheduleTeam.getWKPGRPID(), scheduleTeam.getScheduleTeamCd()))
				.collect(Collectors.toList());

		return AtomTask.of(() -> {
			// require.チームを指定して所属スケジュールチームを削除する( チーム.職場グループID, チーム.コード )
			require.getSpecifyTeamAndScheduleTeam(scheduleTeam.getWKPGRPID(), scheduleTeam.getScheduleTeamCd().v());
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
		 * [R-1] チームを指定して所属スケジュールチームを取得する
		 * 
		 * @param companyID
		 * @param WKPGRPID
		 * @param scheduleTeamCd
		 * @return
		 */
		// Ở đây phải là Delete mới đúng --- QA http://192.168.50.4:3000/issues/110711		
		void getSpecifyTeamAndScheduleTeam(String WKPGRPID, String scheduleTeamCd);

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
