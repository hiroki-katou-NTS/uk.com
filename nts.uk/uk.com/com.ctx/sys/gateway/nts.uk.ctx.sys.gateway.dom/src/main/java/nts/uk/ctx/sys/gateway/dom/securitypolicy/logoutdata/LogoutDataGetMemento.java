/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.securitypolicy.logoutdata;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.gateway.dom.login.ContractCode;

/**
 * The Interface LogoutDataGetMemento.
 */
public interface LogoutDataGetMemento {
	
	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public String getUserId();

    /**
     * Gets the logout date time.
     *
     * @return the logout date time
     */
    public GeneralDate getLogoutDateTime();

    /**
     * Gets the log type.
     *
     * @return the log type
     */
    public LogType getLogType();

    /**
     * Gets the contract code.
     *
     * @return the contract code
     */
    public ContractCode getContractCode();

    /**
     * Gets the login method.
     *
     * @return the login method
     */
    public LoginMethod getLoginMethod();
}
