package nts.uk.ctx.sys.portal.dom.toppagesetting;

import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class TopPagePersonSetting.
 */
public class TopPagePersonSetting extends AggregateRoot {

	/** The company id. */
	private String companyId;
	
	/** The employee id. */
	private String employeeId;
	
	/** The top menu code. */
	private String topMenuCd;
	
	/** The login menu code. */
	private String loginMenuCd;

	/**
	 * Instantiates a new top page title set.
	 *
	 * @param companyId the company id
	 * @param employeeId the employee id
	 * @param topMenuCd the top menu code
	 * @param loginMenuCd the login menu code
	 */
	public TopPagePersonSetting(String companyId, String employeeId, String topMenuCd, String loginMenuCd) {
		this.companyId = companyId;
		this.employeeId = employeeId;
		this.topMenuCd = topMenuCd;
		this.loginMenuCd = loginMenuCd;
	} 
	
	/**
	 * Creates the from java type.
	 *
	 * @param companyId the company id
	 * @param employeeId the employee id
	 * @param topMenuCd the top menu code
	 * @param loginMenuCd the login menu code
	 */
	public static TopPagePersonSetting createFromJavaType(String companyId, String employeeId, String topMenuCd, String loginMenuCd) {
		return new TopPagePersonSetting(companyId, employeeId, topMenuCd, loginMenuCd);
	}
}
