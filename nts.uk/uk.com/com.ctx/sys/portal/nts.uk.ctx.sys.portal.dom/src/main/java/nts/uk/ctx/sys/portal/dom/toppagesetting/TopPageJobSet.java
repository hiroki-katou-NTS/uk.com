package nts.uk.ctx.sys.portal.dom.toppagesetting;

import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class TopPageTitleSet.
 */
public class TopPageJobSet extends AggregateRoot {
	
	/** The company id. */
	private String companyId;
	
	/** The job id. */
	private String jobId;
	
	/** The top menu no. */
	private String topMenuCd;
	
	/** The login menu no. */
	private String loginMenuCd;	
	
	/** The person permission set. */
	private int personPermissionSet;

	/**
	 * Instantiates a new top page title set.
	 *
	 * @param companyId the company id
	 * @param jobId the user id
	 * @param topMenuCd the top menu code
	 * @param loginMenuCd the login menu code 
	 * @param personPermissionSet the layout id
	 */
	public TopPageJobSet(String companyId, String jobId, String topMenuCd, String loginMenuCd, int personPermissionSet) {
		this.companyId = companyId;
		this.jobId = jobId;
		this.topMenuCd = topMenuCd;
		this.loginMenuCd = loginMenuCd;
		this.personPermissionSet = personPermissionSet;
	} 
	
	/**
	 * Creates the from java type.
	 *
	 * @param companyId the company id
	 * @param jobId the user id
	 * @param topMenuCd the top menu code
	 * @param loginMenuCd the login menu code 
	 * @param personPermissionSet the layout id
	 */
	public static TopPageJobSet createFromJavaType(String companyId, String jobId, String topMenuCd, String loginMenuCd, int personPermissionSet) {
		return new TopPageJobSet(companyId, jobId, topMenuCd, loginMenuCd, personPermissionSet);
	}
}
