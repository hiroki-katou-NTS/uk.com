package nts.uk.ctx.workflow.ac.employee;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.auth.pub.employee.EmployeePublisher;
import nts.uk.ctx.workflow.dom.adapter.employee.EmployeeWithRangeAdapter;
import nts.uk.ctx.workflow.dom.adapter.employee.EmployeeWithRangeLoginImport;

/**
 * @author sang.nv
 *
 */
@Stateless
public class EmployeeWithRangeImpl implements EmployeeWithRangeAdapter {

	@Inject
	EmployeePublisher employeePublisher;

	@Override
	public Optional<EmployeeWithRangeLoginImport> findEmployeeByAuthorizationAuthority(String companyID,
			String employeeCD) {
		Optional<EmployeeWithRangeLoginImport> employeeWithRangeLoginImport = this.employeePublisher
				.findByCompanyIDAndEmpCD(companyID, employeeCD).map(x -> {
					return new EmployeeWithRangeLoginImport(x.getBusinessName(), x.getEmployeeCD(),
							x.getEmployeeID());
				});
		if (!employeeWithRangeLoginImport.isPresent())
			return Optional.empty();
		return employeeWithRangeLoginImport;
	}

	@Override
	public Optional<EmployeeWithRangeLoginImport> findByEmployeeByLoginRange(String companyID, String employeeCD, GeneralDate baseDate) {
		Optional<EmployeeWithRangeLoginImport> employeeWithRangeLoginImport = this.employeePublisher
				.getByComIDAndEmpCD(companyID, employeeCD, baseDate).map(x -> {
					return new EmployeeWithRangeLoginImport(x.getBusinessName(), x.getEmployeeCD(),
							x.getEmployeeID());
				});
		if (!employeeWithRangeLoginImport.isPresent())
			return Optional.empty();
		return employeeWithRangeLoginImport;
	}
}
