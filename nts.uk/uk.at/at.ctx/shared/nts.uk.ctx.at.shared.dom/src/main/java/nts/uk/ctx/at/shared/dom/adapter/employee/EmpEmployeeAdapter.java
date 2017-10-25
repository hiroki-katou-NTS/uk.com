/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.adapter.employee;

/**
 * The Interface EmpEmployeeAdapter.
 */
public interface EmpEmployeeAdapter {

	/**
	 * Find by emp id.
	 *
	 * @param empId the emp id
	 * @return the employee imported
	 */
	EmployeeImported findByEmpId(String empId);
}
