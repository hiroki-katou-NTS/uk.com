package nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.domainservice;

import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeamCd;

/**
 * スケジュールチームを削除する
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務予定.社員情報.スケジュールチーム
 * @author HieuLT
 *
 */
public class DeleteScheduleTeamService {
	/**
	 * [1] 削除する
	 * @param require
	 * @param WKPGRPID
	 * @param scheduleTeamCd
	 * @return
	 */
	public static AtomTask delete(Require require,String WKPGRPID,ScheduleTeamCd scheduleTeamCd){
		
		return AtomTask.of(() -> {
			require.deleteScheduleTeam( WKPGRPID, scheduleTeamCd);
			require.deleteBelongScheduleTeam( WKPGRPID, scheduleTeamCd);
		});	
	} 
	
	public static interface Require{
		
		
		/**
		 * [R-1] スケジュールチームを削除する			
		 * @param companyID
		 * @param WKPGRPID
		 * @param scheduleTeamCd
		 */
		public void deleteScheduleTeam(String WKPGRPID ,ScheduleTeamCd scheduleTeamCd);
		 
		/**
		 * [R-2] 所属スケジュールチームを削除する			
		 * @param companyID
		 * @param WKPGRPID
		 * @param scheduleTeamCd
		 */
		public void deleteBelongScheduleTeam(String WKPGRPID , ScheduleTeamCd scheduleTeamCd);
		 
	}

}
