package nts.uk.ctx.sys.portal.dom.toppagesetting;

import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class TopPagePersonSetting.
 */
public class TopPagePersonSetting extends AggregateRoot {

	/** The company id. */
	private String companyId;
	
	/** The top menu no. */
	private String topMenuNo;
	
	/** The login menu no. */
	private String loginMenuNo;
	
	/** The employee id. */
	private String employeeId;

	/**
	 * Instantiates a new top page title set.
	 *
	 * @param companyId the company id
	 * @param topMenuNo the top menu no
	 * @param loginMenuNo the login menu no
	 * @param employeeId the employee id
	 */
	public TopPagePersonSetting(String companyId, String topMenuNo, String loginMenuNo, String employeeId) {
		this.companyId = companyId;
		this.topMenuNo = topMenuNo;
		this.loginMenuNo = loginMenuNo;
		this.employeeId = employeeId;
	} 
	
	/**
	 * Creates the from java type.
	 *
	 * @param companyId the company id
	 * @param topMenuNo the top menu no
	 * @param loginMenuNo the login menu no
	 * @param employeeId the employee id
	 */
	public static TopPagePersonSetting createFromJavaType(String companyId, String topMenuNo, String loginMenuNo, String employeeId) {
		return new TopPagePersonSetting(companyId, topMenuNo, loginMenuNo, employeeId);
	}
}
