/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.login;

/**
 * The Interface EmployeeGetMemento.
 */
public interface EmployeeGetMemento {

	/**
	 * Gets the business name.
	 *
	 * @return the business name
	 */
	public String getBusinessName();

    /**
     * Gets the company id.
     *
     * @return the company id
     */
    public String getCompanyId();

    /**
     * Gets the employee id.
     *
     * @return the employee id
     */
    public Integer getEmployeeId();

    /**
     * Gets the employee code.
     *
     * @return the employee code
     */
    public EmployeeCode getEmployeeCode();
}
