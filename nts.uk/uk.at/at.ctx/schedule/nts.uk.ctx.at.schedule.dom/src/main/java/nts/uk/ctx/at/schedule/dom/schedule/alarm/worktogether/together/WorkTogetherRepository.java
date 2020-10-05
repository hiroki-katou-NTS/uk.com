package nts.uk.ctx.at.schedule.dom.schedule.alarm.worktogether.together;

import java.util.List;
import java.util.Optional;

/**
 * 同時出勤指定Repository
 * @author hiroko_miura
 *
 */
public interface WorkTogetherRepository {

	/**
	 * insert ( 同時出勤指定 )
	 * @param simulAttDes
	 */
	void insert (WorkTogether simulAttDes);
	
	/**
	 * update ( 同時出勤指定 )
	 * @param simulAttDes
	 */
	void update (WorkTogether simulAttDes);
	
	/**
	 * delete ( 社員ID )
	 * @param employeeId
	 */
	void delete (String employeeId);
	
	
	/**
	 * *getAll ( 会社ID )
	 * @param companyId
	 * @return
	 */
	List<WorkTogether> getAll (String companyId);
	
	/**
	 * get ( 社員ID )
	 * @param employeeId
	 * @return
	 */
	Optional<WorkTogether> get (String employeeId);
	
	/**
	 * *get ( List<社員ID> )
	 * @param employeeIdList
	 * @return
	 */
	List<WorkTogether> getWithEmpIdList (List<String> employeeIdList);
	
	/**
	 * exists ( 社員ID )
	 * @param employeeId
	 * @return
	 */
	boolean exists (String employeeId);
}
