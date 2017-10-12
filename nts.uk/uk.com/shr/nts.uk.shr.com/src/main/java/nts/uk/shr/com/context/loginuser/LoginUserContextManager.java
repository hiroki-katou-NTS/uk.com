package nts.uk.shr.com.context.loginuser;

/**
 * The class to update LoginUserContext
 * 
 * usage:
 * @Inject LoginUserContextManager manager;
 * ...
 *
 * this.manager.loggedInAsEmployee(... parameters ...);
 * 
 * this.manager.roleIdSetter();
 *     .forAttendance(...);
 *     .forPayroll(...);
 *     .forPersonnel(...);
 *     .forPersonalInfo(...);
 * ...
 */
public interface LoginUserContextManager {
	
	/**
	 * User logged in as empoyee.
	 * 
	 * @param userId
	 * @param personId
	 * @param contractCode
	 * @param companyId
	 * @param companyCode
	 * @param employeeId
	 * @param employeeCode
	 */
	void loggedInAsEmployee(
			String userId,
			String personId,
			String contractCode,
			String companyId,
			String companyCode,
			String employeeId,
			String employeeCode);
	
	/**
	 * Returns RoleIdSetter to set role IDs of the user.
	 * @return RoleIdSetter
	 */
	RoleIdSetter roleIdSetter(); 
	
	public static interface RoleIdSetter {

		RoleIdSetter forAttendance(String forPersonInCharge, String forGeneral);
		RoleIdSetter forPayroll(String forPersonInCharge, String forGeneral);
		RoleIdSetter forPersonnel(String forPersonInCharge, String forGeneral);
		RoleIdSetter forPersonalInfo(String forPersonInCharge, String forGeneral);
	}
}
