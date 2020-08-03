package nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam;

import java.util.List;
import java.util.Optional;
/** スケジュールチーム Repossitory **/
public interface ScheduleTeamRepository {
	
	/**
	 * [0] insert	
	 * スケジュールチームを新規登録する		
	 * @param scheduleTeam
	 * @param scheduleTeamOrder
	 */
	public void insert(ScheduleTeam scheduleTeam);
	
	/**
	 * [1-1] update ( スケジュールチーム )			
	 * @param scheduleTeam
	 */
	
	public void update(ScheduleTeam scheduleTeam );

	
	/**
	 * [2] delete ( 会社ID, 職場グループID, スケジュールチームコード )
	 * スケジュールチームを削除し、並び順を補正する
	 * @param companyID
	 * @param WKPGRPID
	 * @param scheduleTeamCd
	 */
	public void delete(String companyID ,  String WKPGRPID ,String scheduleTeamCd);
	/**
	 * [3-1] 職場グループ内のスケジュールチームをすべて取得する
	 * @param companyID
	 * @param WKPGRPID
	 * @return 
	 */
	public List<ScheduleTeam> getAllScheduleTeamWorkgroup (String companyID , String WKPGRPID  );
	
	/**
	 * [3-2] 指定された職場グループ内のスケジュールチームをすべて取得する
	 * @param companyID
	 * @param listWKPGRPID
	 * @return
	 */
	public List<ScheduleTeam> getAllSchedule(String companyID , List<String> listWKPGRPID );
	/**
	 * [4-1] スケジュールチームを取得する
	 * @param companyID
	 * @param WKPGRPID
	 * @param scheduleTeamCd
	 * @return
	 */
	public Optional<ScheduleTeam> getScheduleTeam(String companyID ,String WKPGRPID ,String scheduleTeamCd);
	
	/**
	 * [4-2] 指定されたスケジュールチームをすべて取得する			
	 * @param companyID
	 * @param WKPGRPID
	 * @param scheduleTeamCd
	 * @return
	 */
	
	
	/** Hoi lai Dan **/
	/**
	 * [4-3] 指定されたスケジュールチームが存在するか	
	 * @param companyID
	 * @param WKPGRPID
	 * @param scheduleTeamCd
	 * @return
	 */
	public boolean checkExistScheduleTeam(String companyID ,String WKPGRPID ,String scheduleTeamCd);
	
	/**
	 * 	[5-1] スケジュールチームの並び順を取得する	
	 * @param companyID
	 * @param WKPGRPID
	 * @return
	 */
	
	
	//public boolean checkExistScheduleTeamOrder ()
}
