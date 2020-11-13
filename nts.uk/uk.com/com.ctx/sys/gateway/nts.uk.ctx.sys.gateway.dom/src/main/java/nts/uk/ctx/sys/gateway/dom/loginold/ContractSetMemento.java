/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.loginold;

import nts.arc.time.calendar.period.DatePeriod;

/**
 * The Interface ContractSetMemento.
 */
public interface ContractSetMemento {

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(HashPassword password);

    /**
     * Sets the contract code.
     *
     * @param contractCode the new contract code
     */
    public void setContractCode(ContractCode contractCode);

    /**
     * Sets the contract period.
     *
     * @param contractPeriod the new contract period
     */
    public void setContractPeriod(DatePeriod contractPeriod);
}
