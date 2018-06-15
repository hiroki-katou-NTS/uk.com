package nts.uk.ctx.sys.gateway.dom.securitypolicy.lockoutdata;

import lombok.Value;

/*
 * @author: Nguyen Van Hanh
 */
@Value
public class SearchUser {
	String userID;
	String loginID;
	String userName;

	public SearchUser(String userID, String loginID, String userName) {
		this.userID = userID;
		this.loginID = loginID;
		this.userName = userName;
	}
}
