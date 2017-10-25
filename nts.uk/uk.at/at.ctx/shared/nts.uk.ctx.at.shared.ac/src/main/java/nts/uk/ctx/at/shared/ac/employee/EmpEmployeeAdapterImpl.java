package nts.uk.ctx.at.shared.ac.employee;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeImported;
import nts.uk.ctx.bs.employee.pub.employee.SyEmployeePub;

@Stateless
public class EmpEmployeeAdapterImpl implements EmpEmployeeAdapter {
	
	@Inject
	private SyEmployeePub employeePub;

	@Override
	public List<EmployeeImported> findByListId(String companyId, List<String> empIdList) {
//		List<EmployeeImported> empDto = employeePub
		return null;
	}

}
