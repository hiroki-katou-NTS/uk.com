/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.login;

/**
 * The Interface EmployeeCodeSettingGetMemento.
 */
public interface EmployeeCodeSettingGetMemento {

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	public String getCompanyId();

    /**
     * Gets the number digit.
     *
     * @return the number digit
     */
    public Integer getNumberDigit();

    /**
     * Gets the edits the type.
     *
     * @return the edits the type
     */
    public EmployCodeEditType getEditType();
}
