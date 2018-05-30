/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.adapter.user;

import lombok.Builder;
import lombok.Getter;
import nts.arc.time.GeneralDate;

/**
 * The Class UserImport.
 */
@Getter
@Builder
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
	
	private PassStatus passStatus;
}
