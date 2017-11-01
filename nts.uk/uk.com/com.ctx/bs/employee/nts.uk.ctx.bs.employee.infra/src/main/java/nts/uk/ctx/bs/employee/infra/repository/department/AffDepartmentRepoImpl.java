/**
 * 
 */
package nts.uk.ctx.bs.employee.infra.repository.department;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.department.AffDepartmentRepository;
import nts.uk.ctx.bs.employee.dom.department.AffiliationDepartment;

/**
 * @author danpv
 *
 */
@Stateless
public class AffDepartmentRepoImpl implements AffDepartmentRepository{

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.department.AffDepartmentRepository#getByEmpIdAndStandDate(java.lang.String, nts.arc.time.GeneralDate)
	 */
	@Override
	public Optional<AffiliationDepartment> getByEmpIdAndStandDate(String employeeId, GeneralDate standandDate) {
		// TODO Auto-generated method stub
		return null;
	}

}
