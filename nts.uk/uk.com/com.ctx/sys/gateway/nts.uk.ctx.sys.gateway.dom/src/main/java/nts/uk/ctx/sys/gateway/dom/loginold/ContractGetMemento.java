/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.loginold;

import nts.arc.time.calendar.period.DatePeriod;

/**
 * The Interface ContractGetMemento.
 */
public interface ContractGetMemento {

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public HashPassword getPassword();

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
    public DatePeriod getContractPeriod();
}
