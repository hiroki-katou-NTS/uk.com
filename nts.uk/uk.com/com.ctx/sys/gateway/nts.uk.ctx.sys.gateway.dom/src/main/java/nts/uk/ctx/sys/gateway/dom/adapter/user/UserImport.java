/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.adapter.user;

import lombok.Getter;
import nts.arc.time.GeneralDate;

/**
 * The Class UserImport.
 */
//@Builder
@Getter
public class UserImport {
	
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

	public UserImport(String userId, String password, String loginId, String mailAddress, String userName,
			String associatePersonId, String contractCode, GeneralDate expirationDate) {
		super();
		this.userId = userId;
		this.password = password;
		this.loginId = loginId;
		this.mailAddress = mailAddress;
		this.userName = userName;
		this.associatePersonId = associatePersonId;
		this.contractCode = contractCode;
		this.expirationDate = expirationDate;
	}
	
	
}
