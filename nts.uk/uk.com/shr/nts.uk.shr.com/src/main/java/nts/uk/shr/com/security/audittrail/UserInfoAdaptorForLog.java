package nts.uk.shr.com.security.audittrail;

import java.util.List;

import nts.uk.shr.com.security.audittrail.correction.content.UserInfo;

public interface UserInfoAdaptorForLog {

	UserInfo findByEmployeeId(String employeeId);
	
	List<UserInfo> findByEmployeeId(List<String> employeeIds);

	UserInfo findByUserId(String userId);
	
	List<UserInfo> findByUserId(List<String> userIds);
}
