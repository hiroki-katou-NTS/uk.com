/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.employee;

import java.util.List;

/**
 * The Interface EmployeeRepository.
 */
public interface EmployeeRepository {
	
	/**
	 * Adds the employee.
	 *
	 * @param employee the employee
	 */
	void add(Employee employee);
	
	
	/**
	 * Update.
	 *
	 * @param employee the employee
	 */
	void update(Employee employee);
	
	
	
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	List<Employee> findAll(String companyId);

}
