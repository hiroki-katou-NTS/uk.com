package nts.uk.shr.com.context.loginuser;

public interface LoginUserContextManager {

	void loggedInAsEmployee(
			String userId,
			String loginCode,
			String personId,
			String contractCode,
			String companyId,
			String companyCode,
			String employeeId,
			String employeeCode);
	
}
