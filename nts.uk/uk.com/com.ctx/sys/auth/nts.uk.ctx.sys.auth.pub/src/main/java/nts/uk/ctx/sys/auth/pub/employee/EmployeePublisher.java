package nts.uk.ctx.sys.auth.pub.employee;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface EmployeePublisher {
	/** RequestList338 */
	Optional<NarrowEmpByReferenceRange> findByEmpId(List<String> sID, int roleType);

	/** RequestList314 */
	Optional<EmpWithRangeLogin> findByCompanyIDAndEmpCD(String companyID, String employeeCD);
	
	/** RequestList315 */
	Optional<EmpWithRangeLogin> getByComIDAndEmpCD(String companyID , String employeeCD , GeneralDate referenceDate);
	
	/** RequestList334 [No.334]基準日、指定社員から参照可能な職場リストを取得する **/
	List<String> getListWorkPlaceID(String employeeID , GeneralDate referenceDate);
	
}
