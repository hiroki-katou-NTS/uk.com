/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.login;

import nts.arc.time.GeneralDate;

/**
 * The Interface UserGetMemento.
 */
public interface UserGetMemento {

	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public String getUserId();

    /**
     * Gets the password.
     *
     * @return the password
     */
    public HashPassword getPassword();

    /**
     * Gets the login id.
     *
     * @return the login id
     */
    public LoginId getLoginId();

    /**
     * Gets the contract code.
     *
     * @return the contract code
     */
    public ContractCode getContractCode();

    /**
     * Gets the expiration date.
     *
     * @return the expiration date
     */
    public GeneralDate getExpirationDate();

    /**
     * Checks if is special user.
     *
     * @return true, if is special user
     */
    public boolean isSpecialUser();

    /**
     * Checks if is multi company concurrent.
     *
     * @return true, if is multi company concurrent
     */
    public boolean isMultiCompanyConcurrent();

    /**
     * Gets the mail address.
     *
     * @return the mail address
     */
    public MailAddress getMailAddress();

    /**
     * Gets the user name.
     *
     * @return the user name
     */
    public UserName getUserName();

    /**
     * Gets the associated person id.
     *
     * @return the associated person id
     */
    public String getAssociatedPersonId();

}
