package nts.uk.shr.com.security.audittrail;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * ユーザ情報
 */
@RequiredArgsConstructor
public class UserInfo {

	/** ユーザID */
	@Getter
	private final String userId;
	
	/** ユーザ名 */
	@Getter
	private final String userName;
	
}