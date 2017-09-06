/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.employee.department;

import nts.uk.ctx.basic.dom.common.history.Period;

public interface AffiliationDepartmentSetMemento {
	
	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	void setId(String id);
	/**
	 * Sets the.
	 *
	 * @param period the period
	 */
	void setPeriod(Period period);
	
	
	/**
	 * Sets the employee id.
	 *
	 * @param employeeId the new employee id
	 */
	void setEmployeeId(String employeeId);
	
	/**
	 * Sets the department id.
	 *
	 * @param departmentId the new department id
	 */
	void setDepartmentId(String departmentId);
}
