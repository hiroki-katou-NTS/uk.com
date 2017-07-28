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
@Getter
public class User {

	/** The user id. */
	private UserId userId;

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

	/** The Mail address. */
	private MailAddress mailAddress;

	/** The user name. */
	private UserName userName;

	/** The associated employee id. */
	private String associatedEmployeeId;

	/**
	 * @param userId
	 * @param password
	 * @param loginId
	 * @param contractCode
	 * @param expirationDate
	 * @param specialUser
	 * @param multiCompanyConcurrent
	 * @param mailAddress
	 * @param userName
	 * @param associatedEmployeeId
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
