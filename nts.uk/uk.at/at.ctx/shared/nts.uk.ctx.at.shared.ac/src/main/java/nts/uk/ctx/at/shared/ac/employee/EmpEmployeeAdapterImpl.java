package nts.uk.ctx.at.shared.ac.employee;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeImported;
import nts.uk.ctx.bs.employee.pub.employee.SyEmployeePub;
import nts.uk.ctx.bs.person.pub.person.PersonPub;

@Stateless
public class EmpEmployeeAdapterImpl implements EmpEmployeeAdapter {
	
	@Inject
	private SyEmployeePub employeePub;
	
	@Inject
	private PersonPub personPub;
	

	@Override
	public EmployeeImported findByEmpId(String empId) {
//		List<EmployeeImported> empDto = employeePub
		return null;
	}

}
