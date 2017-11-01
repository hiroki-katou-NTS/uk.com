/**
 * 
 */
package nts.uk.ctx.bs.employee.dom.department;

import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * @author danpv
 *
 */
public interface AffDepartmentRepository {

	public Optional<AffiliationDepartment> getByEmpIdAndStandDate(String employeeId, GeneralDate standandDate);

}
