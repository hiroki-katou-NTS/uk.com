/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.login;

import nts.arc.time.GeneralDate;

/**
 * The Interface UserSetMemento.
 */
public interface UserSetMemento {

	/**
	 * Sets the user id.
	 *
	 * @param userId the new user id
	 */
	public void setUserId(String userId);

    /**
     * Sets the password.
     *
     * @param password the new password
     */
    public void setPassword(HashPassword password);

    /**
     * Sets the login id.
     *
     * @param loginId the new login id
     */
    public void setLoginId(LoginId loginId);

    /**
     * Sets the contract code.
     *
     * @param contractCode the new contract code
     */
    public void setContractCode(ContractCode contractCode);

    /**
     * Sets the expiration date.
     *
     * @param expirationDate the new expiration date
     */
    public void setExpirationDate(GeneralDate expirationDate);

    /**
     * Sets the special user.
     *
     * @param specialUser the new special user
     */
    public void setSpecialUser(boolean specialUser);

    /**
     * Sets the multi company concurrent.
     *
     * @param multiCompanyConcurrent the new multi company concurrent
     */
    public void setMultiCompanyConcurrent(boolean multiCompanyConcurrent);

    /**
     * Sets the mail address.
     *
     * @param MailAddress the new mail address
     */
    public void setMailAddress(MailAddress MailAddress);

    /**
     * Sets the user name.
     *
     * @param userName the new user name
     */
    public void setUserName(UserName userName);

    /**
     * Sets the associated person id.
     *
     * @param associatedPersonId the new associated person id
     */
    public void setAssociatedPersonId(String associatedPersonId);
}
