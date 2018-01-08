package nts.uk.shr.com.context;

import nts.uk.shr.com.context.loginuser.SelectedLanguage;
import nts.uk.shr.com.context.loginuser.role.LoginUserRoles;

public interface LoginUserContext {
	
	public static final String KEY_SESSION_SCOPED = "LoginUserContext";
	
	boolean hasLoggedIn();
	
	boolean isEmployee();
	
	/**
	 * Returns user ID.
	 * @return user ID
	 */
	String userId();
	
	/**
	 * Returns person ID.
	 * @return person ID
	 */
	String personId();
	
	/**
	 * Returns contract code.
	 * @return contract code
	 */
	String contractCode();
	
	/**
	 * Returns company ID.
	 * @return company ID
	 */
	String companyId();
	
	/**
	 * Returns company code.
	 * @return company code
	 */
	String companyCode();
	
	/**
	 * Returns employee ID.
	 * @return employee ID
	 */
	String employeeId();
	
	/**
	 * Returns employee code.
	 * @return employee code
	 */
	String employeeCode();
	
	/**
	 * Returns roles.
	 * @return roles
	 */
	LoginUserRoles roles();
	
	/**
	 * Returns language ID
	 * @return language ID
	 */
	SelectedLanguage language();
}
