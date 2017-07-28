/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.login;

import lombok.Getter;
import nts.arc.time.GeneralDate;

/**
 * The Class User.
 */

/**
 * Gets the associated employee id.
 *
 * @return the associated employee id
 */
@Getter
public class User {

	/** The user id. */
	private String userId;

	/** The password. */
	private HashPassword password;

	/** The login id. */
	private LoginId loginId;

	/** The contract code. */
	private ContractCode contractCode;

	/** The expiration date. */
	private GeneralDate expirationDate;

	/** The special user. */
	private boolean specialUser;

	/** The multi company concurrent. */
	private boolean multiCompanyConcurrent;

	/** The mail address. */
	private MailAddress mailAddress;

	/** The user name. */
	private UserName userName;

	/** The associated employee id. */
	private String associatedEmployeeId;

	/**
	 * Instantiates a new user.
	 *
	 * @param memento the memento
	 */
	public User(UserGetMemento memento) {
		this.userId = memento.getUserId();
		this.password = memento.getPassword();
		this.loginId = memento.getLoginId();
		this.contractCode = memento.getContractCode();
		this.expirationDate = memento.getExpirationDate();
		this.specialUser = memento.isSpecialUser();
		this.multiCompanyConcurrent = memento.isMultiCompanyConcurrent();
		this.mailAddress = memento.getMailAddress();
		this.userName = memento.getUserName();
		this.associatedEmployeeId = memento.getAssociatedEmployeeId();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(UserSetMemento memento) {
		memento.setUserId(this.userId);
		memento.setPassword(this.password);
		memento.setLoginId(this.loginId);
		memento.setContractCode(this.contractCode);
		memento.setExpirationDate(this.expirationDate);
		memento.setSpecialUser(this.specialUser);
		memento.setMultiCompanyConcurrent(this.multiCompanyConcurrent);
		memento.setMailAddress(this.mailAddress);
		memento.setUserName(this.userName);
		memento.setAssociatedEmployeeId(this.associatedEmployeeId);
	}
}
