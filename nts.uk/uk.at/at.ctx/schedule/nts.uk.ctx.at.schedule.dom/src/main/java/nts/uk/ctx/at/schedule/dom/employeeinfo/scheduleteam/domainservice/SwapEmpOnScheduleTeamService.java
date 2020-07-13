package nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.domainservice;

import java.util.List;

import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.BelongScheduleTeam;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeam;

//スケジュールチームに所属する社員を入れ替える
/**
 * スケジュールチームに所属する社員を入れ替える
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務予定.社員情報.スケジュールチーム
 * @author HieuLt
 *
 */


public class SwapEmpOnScheduleTeamService {

	
	public static AtomTask replace(Require require ,ScheduleTeam scheduleTeam , List<String> lstEmpID){
		return null;
	}
	
	
	public static interface Require{
		/**
		 * [R-1] チームを指定して所属スケジュールチームを取得する		
		 * @param companyID
		 * @param WKPGRPID
		 * @param scheduleTeamCd
		 * @return
		 */
		List<BelongScheduleTeam> getSpecifyTeamAndScheduleTeam(String companyID , String WKPGRPID ,String scheduleTeamCd );
		
		/**
		 * [R-2] 社員が既にチームに所属しているか					
		 * @param companyID
		 * @param empID
		 * @return
		 */
		boolean empBelongTeam(String companyID , String empID);
		
		/**
		 * [R-3] 社員を指定して所属スケジュールチームを削除する		
		 * @param companyID
		 * @param empID
		 */
		void delete(String companyID , String empID);
		
		/**
		 * [R-4] 所属スケジュールチームを追加する		
		 * @param belongScheduleTeam
		 */
	    void insert(BelongScheduleTeam belongScheduleTeam);
		
	}
}
