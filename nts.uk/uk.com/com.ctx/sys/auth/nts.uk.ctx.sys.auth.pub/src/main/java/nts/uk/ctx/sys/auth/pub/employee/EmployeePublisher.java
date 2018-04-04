package nts.uk.ctx.sys.auth.pub.employee;

import java.util.List;
import java.util.Optional;

public interface EmployeePublisher {
	/** RequestList338 */
	Optional<NarrowEmpByReferenceRange> findByEmpId(List<String> sID);

	/** RequestList314 */
	Optional<EmpWithRangeLogin> findByCompanyIDAndEmpCD(String companyID, String employeeCD);
	
	/** RequestList315 */
	Optional<EmpWithRangeLogin> getByComIDAndEmpCD(String companyID , String employeeCD);
	
}
