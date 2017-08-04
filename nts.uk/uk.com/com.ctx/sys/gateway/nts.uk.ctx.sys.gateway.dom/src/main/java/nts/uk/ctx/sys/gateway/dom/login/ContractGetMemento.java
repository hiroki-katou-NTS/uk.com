/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.login;

/**
 * The Interface ContractGetMemento.
 */
public interface ContractGetMemento {

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public RawPassword getPassword();

    /**
     * Gets the contract code.
     *
     * @return the contract code
     */
    public ContractCode getContractCode();

    /**
     * Gets the contract period.
     *
     * @return the contract period
     */
    public Period getContractPeriod();
}
