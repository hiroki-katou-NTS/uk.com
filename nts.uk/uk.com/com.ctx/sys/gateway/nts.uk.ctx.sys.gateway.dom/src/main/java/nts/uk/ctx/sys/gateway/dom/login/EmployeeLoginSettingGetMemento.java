/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.login;

/**
 * The Interface EmployeeLoginSettingGetMemento.
 */
public interface EmployeeLoginSettingGetMemento {
	
	/**
	 * Gets the contract code.
	 *
	 * @return the contract code
	 */
	public ContractCode getContractCode();

    /**
     * Gets the form 2 permit atr.
     *
     * @return the form 2 permit atr
     */
    public ManageDistinct getForm2PermitAtr();

    /**
     * Gets the form 3 permit atr.
     *
     * @return the form 3 permit atr
     */
    public ManageDistinct getForm3PermitAtr();
}
