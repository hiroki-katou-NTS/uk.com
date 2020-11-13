/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.loginold;

/**
 * The Interface EmployeeLoginSettingSetMemento.
 */
public interface EmployeeLoginSettingSetMemento {

	/**
	 * Sets the contract code.
	 *
	 * @param contractCode the new contract code
	 */
	public void setContractCode(ContractCode contractCode);

	/**
	 * Sets the form 2 permit atr.
	 *
	 * @param form2PermitAtr the new form 2 permit atr
	 */
	public void setForm2PermitAtr(ManageDistinct form2PermitAtr);

	/**
	 * Sets the form 3 permit atr.
	 *
	 * @param form3PermitAtr the new form 3 permit atr
	 */
	public void setForm3PermitAtr(ManageDistinct form3PermitAtr);
}
