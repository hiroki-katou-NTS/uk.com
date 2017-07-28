/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.login;

/**
 * The Interface EmployeeCodeSettingSetMemento.
 */
public interface EmployeeCodeSettingSetMemento {

	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	public void setCompanyId(String companyId);

    /**
     * Sets the number digit.
     *
     * @param numberDigit the new number digit
     */
    public void setNumberDigit(Integer numberDigit);

    /**
     * Sets the edits the type.
     *
     * @param editType the new edits the type
     */
    public void setEditType(EmployCodeEditType editType);
}
