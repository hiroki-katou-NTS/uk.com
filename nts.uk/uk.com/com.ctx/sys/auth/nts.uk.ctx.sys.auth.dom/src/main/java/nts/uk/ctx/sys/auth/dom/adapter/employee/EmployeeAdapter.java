package nts.uk.ctx.sys.auth.dom.adapter.employee;

import java.util.List;

import nts.uk.ctx.sys.auth.dom.employee.dto.EmployeeImport;

public interface EmployeeAdapter {
	EmployeeImport findByEmpId(String empId);
	
	List<EmployeeImport> findByEmployeeId(String employeeId);
}
