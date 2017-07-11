/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.employee;

import java.util.List;
import java.util.Optional;

/**
 * The Interface EmployeeRepository.
 */
public interface EmployeeRepository {
	
	/**
	 * Gets the person id by employee code.
	 *
	 * @param companyId the company id
	 * @param employeeCode the employee code
	 * @return the person id by employee code
	 */
	Optional<Employee> getPersonIdByEmployeeCode(String companyId, String employeeCode);
	
	/**
	 * Gets the list person by list employee.
	 *
	 * @param companyId the company id
	 * @param employeeCode the employee code
	 * @return the list person by list employee
	 */
	List<Employee> getListPersonByListEmployee(String companyId, List<String> employeeCode);
	
	
	/**
	 * Gets the all employee.
	 *
	 * @param companyId the company id
	 * @return the all employee
	 */
	List<Employee> getAllEmployee(String companyId);
	
	
	/**
	 * Gets the list person by list employee.
	 *
	 * @param companyId the company id
	 * @param employeeIds the employee ids
	 * @return the list person by list employee
	 */
	List<Employee> getListPersonByListEmployeeId(String companyId, List<String> employeeIds);
}
