package nts.uk.shr.com.security.audittrail;

public interface UserInfoAdaptorForLog {

	UserInfo findByEmployeeId(String employeeId);

	UserInfo findByUserId(String userId);
}
