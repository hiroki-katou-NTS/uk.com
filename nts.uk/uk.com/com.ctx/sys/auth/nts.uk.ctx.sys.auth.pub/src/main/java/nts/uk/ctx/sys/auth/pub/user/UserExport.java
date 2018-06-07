/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.pub.user;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.gul.text.StringUtil;

/**
 * Gets the associated person ID.
 *
 * @return the associated person ID
 */
@Getter
public class UserExport {
	
	/** The user ID. */
	private String userID;
	
	/** The password. */
	private String password;

	/** The login ID. */
	private String loginID;
	
	/** The mail address. */
	private String mailAddress;

	/** The contract code. */
	private String contractCode;
	
	/** The user name. */
	private String userName;
	
	/** The expiration date. */
	private GeneralDate expirationDate;

	/** The associated person ID. */
	private String associatedPersonID;
	
	/** The associated person ID. */
	private int passStatus;

	/**
	 * Instantiates a new user export.
	 *
	 * @param userID the user ID
	 * @param loginID the login ID
	 * @param contractCode the contract code
	 * @param userName the user name
	 * @param password the password
	 * @param mailAddress the mail address
	 * @param associatedPersonID the associated person ID
	 * @param expirationDate the expiration date
	 */
	public UserExport(String userID, String loginID, String contractCode, String userName, String password, String mailAddress, String associatedPersonID,
			GeneralDate expirationDate ,int passStatus) {
		this.userID = userID;
		this.loginID = loginID;
		this.contractCode = contractCode;
		this.userName = userName;
		this.password = password;
		this.mailAddress = mailAddress;
		this.associatedPersonID = associatedPersonID;
		this.expirationDate = expirationDate;
		this.passStatus = passStatus;
	}	
	
	public boolean isExistAssociatedPersonID(String associatedPersonID){
		return !StringUtil.isNullOrEmpty(this.associatedPersonID, false);
	}
	
}