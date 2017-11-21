package nts.uk.ctx.sys.auth.ac.employee;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.pub.employee.EmployeeBasicInfoExport;
import nts.uk.ctx.bs.employee.pub.employee.SyEmployeePub;
import nts.uk.ctx.sys.auth.dom.adapter.employee.EmployeeAdapter;
import nts.uk.ctx.sys.auth.dom.employee.dto.EmployeeImport;

@Stateless
public class AuthEmployeeAdapterImpl implements EmployeeAdapter{

	@Inject
	private SyEmployeePub employeePub;
	
	private EmployeeImport toImport(EmployeeBasicInfoExport export){
		return new EmployeeImport(export.getPName(), export.getEmpId(), export.getEmpCode());
	}
	
	@Override
	public EmployeeImport findByEmpId(String empId) {
		return toImport(employeePub.findByEmpId(empId));
	}

}
