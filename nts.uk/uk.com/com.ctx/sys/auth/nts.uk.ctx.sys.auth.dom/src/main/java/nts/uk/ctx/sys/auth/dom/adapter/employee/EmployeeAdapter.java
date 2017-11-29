package nts.uk.ctx.sys.auth.dom.adapter.employee;

import nts.uk.ctx.sys.auth.dom.employee.dto.EmployeeImport;

public interface EmployeeAdapter {
	EmployeeImport findByEmpId(String empId);
}
