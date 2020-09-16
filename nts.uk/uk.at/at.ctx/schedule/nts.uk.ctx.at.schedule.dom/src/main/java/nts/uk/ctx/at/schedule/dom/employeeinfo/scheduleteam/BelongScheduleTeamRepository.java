package nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam;

import java.util.List;
import java.util.Optional;

public interface BelongScheduleTeamRepository {
	/**
	 * [0] insert ( 所属スケジュールチーム )	
	 * @param belongScheduleTeam
	 */
	public void insert(BelongScheduleTeam belongScheduleTeam);
	/**
	 * [1] update ( 所属スケジュールチーム )
	 * @param belongScheduleTeam
	 */
	public void update(BelongScheduleTeam belongScheduleTeam);
	/**
	 * [2-1] delete ( 会社ID, 社員ID, 職場グループID, スケジュールチームコード )
	 * @param companyID
	 * @param empID
	 * @param WKPGRPID
	 * @param scheduleTeamCd
	 */
	public void delete(String companyID , String empID , String WKPGRPID , String scheduleTeamCd); 
	/**
	 * [2-2] delete ( 会社ID, 社員ID )		
	 * @param companyID
	 * @param empID
	 */
	public void delete(String companyID , String empID);
	/**
	 * [2-3] delete ( 会社ID, 職場グループID, スケジュールチームコード )
	 * @param companyID
	 * @param WKPGRPID
	 * @param scheduleTeamCd
	 */
	public void delete(String companyID , String WKPGRPID , String scheduleTeamCd);
	/**
	 * [3] *getAll ( 会社ID, 職場グループID, スケジュールチームコード )
	 * @param companyID
	 * @param WKPGRPID
	 * @param scheduleTeamCd
	 * @return
	 */
	public List<BelongScheduleTeam> getAll(String companyID , String WKPGRPID , String scheduleTeamCd);
	/**
	 * [4-1] get ( 会社ID, 社員ID )	
	 * @param companyID
	 * @param empID
	 * @return
	 */
	public Optional<BelongScheduleTeam> get(String companyID , String empID);
	/**
	 * [4-2] *get ( 会社ID, List<社員ID> )	
	 * @param companyID
	 * @param empID
	 * @return
	 */
	public List<BelongScheduleTeam> get(String companyID , List<String> empIDs);
	/**
	 * [4-3] exists ( 会社ID, 社員ID )
	 * @param companyID
	 * @param empID
	 * @return
	 */
	public boolean exists(String companyID ,String empID);
	/**
	 * [5-1] get ( 会社ID, 社員ID, 職場グループID, スケジュールチームコード )
	 * @param companyID
	 * @param empID
	 * @param WKPGRPID
	 * @param scheduleTeamCd
	 * @return
	 */
	public  Optional<BelongScheduleTeam> get (String companyID , String empID , String WKPGRPID ,String scheduleTeamCd);
	/**
	 * [5-2] exists ( 会社ID, 社員ID, 職場グループID, スケジュールチームコード )	
	 * @param companyID
	 * @param empID
	 * @param WKPGRPID
	 * @param scheduleTeamCd
	 * @return
	 */
	public boolean exists(String companyID , String empID , String WKPGRPID ,String scheduleTeamCd);
	/**
	 * [6-1] get ( 会社ID, 職場グループID, 社員ID )
	 * @param companyID
	 * @param WKPGRPID
	 * @param empID
	 * @return
	 */
	public Optional<BelongScheduleTeam> get( String companyID,String WKPGRPID,String empID ); 
	/**
	 * [6-2] *get ( 会社ID, 職場グループID, List<社員ID> )
	 * @param companyID
	 * @param WKPGRPID
	 * @param empID
	 * @return
	 */
	public Optional<BelongScheduleTeam> get( String companyID,String WKPGRPID,List<String> empIDs );
	/**
	 * [7] スケジュールチームに所属する社員を取得する			
	 * @param companyID
	 * @param WKPGRPID
	 * @param scheduleTeamCd
	 * @return
	 */
	public List<String> getEmpScheduleTeam(String companyID , String WKPGRPID ,String scheduleTeamCd);
	/**
	 * [8] 社員が所属するスケジュールチームを取得する		
	 * @param companyID
	 * @param empID
	 * @return
	 */
	public Optional<ScheduleTeam> getScheduleTeam(String companyID , String empID);
	/**
	 * [9] 社員がスケジュールチームに所属しているか
	 * @param companyId
	 * @param empID
	 * @return
	 */
	public boolean checkempBelongScheduleTeam (String companyId , String empID);
	
	
}
