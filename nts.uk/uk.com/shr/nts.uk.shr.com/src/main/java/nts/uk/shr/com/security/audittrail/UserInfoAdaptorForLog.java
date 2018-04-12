package nts.uk.shr.com.security.audittrail;

public interface UserInfoAdaptorForLog {

	UserInfo findByEmployeeId(String employeeId);
	
	UserInfo fingByPersonalId(String personalId);
	
	UserInfo findByUserId(String userId);
}
