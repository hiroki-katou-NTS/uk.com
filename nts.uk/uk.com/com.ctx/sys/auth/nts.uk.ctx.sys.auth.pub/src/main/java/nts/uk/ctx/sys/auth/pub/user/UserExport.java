package nts.uk.ctx.sys.auth.pub.user;

import lombok.Getter;

@Getter
public class UserExport {
	
	private String userID;
	
	private String password;

	private String loginID;
	
	private String mailAddress;

	private String contractCode;
	
	private String userName;

	/** Nullable */
	private String associatedPersonID;

	public UserExport(String userID, String loginID, String contractCode, String userName, String password, String mailAddress, String associatedPersonID) {
		this.userID = userID;
		this.loginID = loginID;
		this.contractCode = contractCode;
		this.userName = userName;
		this.password = password;
		this.mailAddress = mailAddress;
		this.associatedPersonID = associatedPersonID;
	}	
}