package nts.uk.ctx.basic.dom.company.organization.employee;

import java.util.List;

public interface EmployeeRepository {
	// get List Employee
	List<Employee> findByEmployeeCode(String companyId, String employeeCode);
}
