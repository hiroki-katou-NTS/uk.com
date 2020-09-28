package nts.uk.ctx.at.schedule.dom.schedule.alarm.simultaneousattendance.designation;

import java.util.List;
import java.util.Optional;

/**
 * 同時出勤指定Repository
 * @author hiroko_miura
 *
 */
public interface SimultaneousAttendanceDesignationRepository {

	/**
	 * insert ( 同時出勤指定 )
	 * @param simulAttDes
	 */
	void insert (SimultaneousAttendanceDesignation simulAttDes);
	
	/**
	 * update ( 同時出勤指定 )
	 * @param simulAttDes
	 */
	void update (SimultaneousAttendanceDesignation simulAttDes);
	
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
	List<SimultaneousAttendanceDesignation> getAll (String companyId);
	
	/**
	 * get ( 社員ID )
	 * @param employeeId
	 * @return
	 */
	Optional<SimultaneousAttendanceDesignation> get (String employeeId);
	
	/**
	 * *get ( List<社員ID> )
	 * @param employeeIdList
	 * @return
	 */
	List<SimultaneousAttendanceDesignation> getWithEmpIdList (List<String> employeeIdList);
	
	/**
	 * exists ( 社員ID )
	 * @param employeeId
	 * @return
	 */
	boolean exists (String employeeId);
}
