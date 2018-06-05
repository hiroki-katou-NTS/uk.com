package nts.uk.shr.com.security.audittrail;

import nts.uk.shr.com.security.audittrail.correction.content.UserInfo;

public interface UserInfoAdaptorForLog {

	UserInfo findByEmployeeId(String employeeId);

	UserInfo findByUserId(String userId);
}
