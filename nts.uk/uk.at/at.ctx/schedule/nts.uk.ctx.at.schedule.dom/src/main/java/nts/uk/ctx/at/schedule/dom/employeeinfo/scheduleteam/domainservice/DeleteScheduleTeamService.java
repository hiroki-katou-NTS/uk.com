package nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.domainservice;

import nts.arc.task.tran.AtomTask;

/**
 * スケジュールチームを削除する
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務予定.社員情報.スケジュールチーム
 * @author kingo
 *
 */
public class DeleteScheduleTeamService {
	
	
	public static interface Require{
		
		public static AtomTask delete(Require require,String WKPGRPID,String scheduleTeamCd){
			return AtomTask.of(() -> {
				require.deleteScheduleTeam( WKPGRPID, scheduleTeamCd);
				require.deleteBelongScheduleTeam( WKPGRPID, scheduleTeamCd);
			});
			
		} 
		
		
		
		/**
		 * [R-1] スケジュールチームを削除する			
		 * @param companyID
		 * @param WKPGRPID
		 * @param scheduleTeamCd
		 */
		public void deleteScheduleTeam(String WKPGRPID ,String scheduleTeamCd);
		 
		/**
		 * [R-2] 所属スケジュールチームを削除する			
		 * @param companyID
		 * @param WKPGRPID
		 * @param scheduleTeamCd
		 */
		public void deleteBelongScheduleTeam(String WKPGRPID , String scheduleTeamCd);
		 
	}

}
