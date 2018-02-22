/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.model.employee;

/**
 * The Interface EmployeeRoleRepository.
 */
public interface EmployeeRoleRepository {
	
	/**
	 * Find role by id.
	 *
	 * @return the employee role imported
	 */
	EmployeeRoleImported findRoleById(String roleId);
}
