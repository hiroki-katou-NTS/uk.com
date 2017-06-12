package nts.uk.ctx.basic.dom.company.organization.employee;

import java.util.Optional;

public interface EmployeeRepository {
	// get List Employee
	Optional<Employee> getPersonIdByEmployeeCode(String companyId, String employeeCode);
}
