package nts.uk.shr.com.security.audittrail;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * ユーザ情報
 */
@RequiredArgsConstructor
@Getter
public class UserInfo {

	/** ユーザID */
	private final String userId;
	
	/** 社員ID */
	private final String employeeId;
	
	/** ユーザ名 */
	private final String userName;
	
	public static UserInfo employee(String userId, String employeeId, String employeeName) {
		return new UserInfo(userId, employeeId, employeeName);
	}
	
	public static UserInfo user(String userId, String userName) {
		return new UserInfo(userId, "", userName);
	}
}