package nts.uk.shr.com.context;

public interface LoginUserContext {
	
	public static final String KEY_SESSION_SCOPED = "LoginUserContext";
	
	public boolean hasLoggedIn();
	
	public boolean isEmployee();
	
	/**
	 * Returns user ID.
	 * @return user ID
	 */
	public String userId();
	
	/**
	 * Returns login code.
	 * @return login code
	 */
	public String loginCode();
	
	/**
	 * Returns person ID.
	 * @return person ID
	 */
	public String personId();
	
	/**
	 * Returns contract code.
	 * @return contract code
	 */
	public String contractCode();
	
	/**
	 * Returns company ID.
	 * @return company ID
	 */
	public String companyId();
	
	/**
	 * Returns company code.
	 * @return company code
	 */
	public String companyCode();
	
	/**
	 * Returns employee ID.
	 * @return employee ID
	 */
	public String employeeId();
	
	/**
	 * Returns employee code.
	 * @return employee code
	 */
	public String employeeCode();
	
	
	
}
