/**
 * 
 */
package nts.uk.ctx.bs.employee.infra.repository.workplace.assigned;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.workplace.assigned.AssignedWorkplace;
import nts.uk.ctx.bs.employee.dom.workplace.assigned.AssignedWrkplcRepository;

/**
 * @author danpv
 *
 */
@Stateless
public class AssignedWrkplcRepoImpl implements AssignedWrkplcRepository{

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.workplace.assigned.AssignedWrkplcRepository#getByEmpIdAndStandDate(java.lang.String, nts.arc.time.GeneralDate)
	 */
	@Override
	public Optional<AssignedWorkplace> getByEmpIdAndStandDate(String employeeId, GeneralDate standandDate) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
