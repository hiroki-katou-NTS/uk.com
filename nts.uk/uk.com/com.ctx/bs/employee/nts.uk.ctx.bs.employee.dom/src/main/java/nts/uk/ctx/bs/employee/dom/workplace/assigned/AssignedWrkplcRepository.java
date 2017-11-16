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

	public Optional<AssignedWorkplace> getByEmpIdAndStandDate(String employeeId, GeneralDate standandDate);

	public AssignedWorkplace getAssignedWorkplaceById(String assignedWorkplaceId);
}
