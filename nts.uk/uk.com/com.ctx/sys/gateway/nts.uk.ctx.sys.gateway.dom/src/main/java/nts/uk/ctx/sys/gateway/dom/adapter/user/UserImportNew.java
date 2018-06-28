/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.adapter.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

/**
 * The Class UserImport.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserImportNew {

	/** The user id. */
	private String userId;

	/** The password. */
	private String password;

	/** The login id. */
	private String loginId;

	/** The mail address. */
	private String mailAddress;

	/** The user name. */
	private String userName;

	/** The person id. */
	private String associatePersonId;

	/** The contract code. */
	private String contractCode;

	/** The expiration date. */
	private GeneralDate expirationDate;

	/** The pass status. */
	private int passStatus;

	public UserImportNew(String userId, String userName, String mailAddress, String loginId, String associatePersonId, String password,
			 String contractCode, GeneralDate expirationDate, Integer passStatus) {
		super();
		this.userId = userId;
		this.password = password;
		this.loginId = loginId;
		this.mailAddress = mailAddress;
		this.userName = userName;
		this.associatePersonId = associatePersonId;
		this.contractCode = contractCode;
		this.expirationDate = expirationDate;
		this.passStatus = passStatus;
	}
}
