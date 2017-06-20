/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository {
	// get List Employee
	Optional<Employee> getPersonIdByEmployeeCode(String companyId, String employeeCode);
	//Get List Person ID
	List<Employee> getListPersonByListEmployee(String companyId, List<String> employeeCode);
	
	
	/**
	 * Gets the all employee.
	 *
	 * @param companyId the company id
	 * @return the all employee
	 */
	List<Employee> getAllEmployee(String companyId);
}
