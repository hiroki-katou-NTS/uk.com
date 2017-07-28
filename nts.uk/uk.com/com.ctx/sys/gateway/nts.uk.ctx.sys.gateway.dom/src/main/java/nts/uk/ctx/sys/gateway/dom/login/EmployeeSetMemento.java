/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.login;

/**
 * The Interface EmployeeSetMemento.
 */
public interface EmployeeSetMemento {

	/**
	 * Sets the business name.
	 *
	 * @param businessName the new business name
	 */
	public void setBusinessName(String businessName);

	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	public void setCompanyId(String companyId);

	/**
	 * Sets the employee id.
	 *
	 * @param employeeId the new employee id
	 */
	public void setEmployeeId(Integer employeeId);

	/**
	 * Sets the employee code.
	 *
	 * @param employeeCode the new employee code
	 */
	public void setEmployeeCode(EmployeeCode employeeCode);
}
