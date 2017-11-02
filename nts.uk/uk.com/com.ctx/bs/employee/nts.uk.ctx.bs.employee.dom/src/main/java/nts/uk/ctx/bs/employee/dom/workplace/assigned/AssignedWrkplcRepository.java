/**
 * 
 */
package nts.uk.ctx.bs.employee.dom.workplace.assigned;

import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * @author danpv
 *
 */
public interface AssignedWrkplcRepository {

	// chua implement
	public Optional<AssignedWorkplace> getByEmpIdAndStandDate(String employeeId, GeneralDate standandDate);

}
