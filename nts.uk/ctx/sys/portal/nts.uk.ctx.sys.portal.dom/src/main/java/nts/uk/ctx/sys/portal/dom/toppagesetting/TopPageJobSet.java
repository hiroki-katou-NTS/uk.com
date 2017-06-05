package nts.uk.ctx.sys.portal.dom.toppagesetting;

import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class TopPageTitleSet.
 */
public class TopPageJobSet extends AggregateRoot {
	
	/** The company id. */
	private String companyId;
	
	/** The top menu no. */
	private String topMenuNo;
	
	/** The login menu no. */
	private String loginMenuNo;
	
	/** The job id. */
	private String jobId;
	
	/** The person permission set. */
	private int personPermissionSet;

	/**
	 * Instantiates a new top page title set.
	 *
	 * @param companyId the company id
	 * @param topMenuNo the top menu no
	 * @param loginMenuNo the login menu no
	 * @param jobId the user id
	 * @param personPermissionSet the layout id
	 */
	public TopPageJobSet(String companyId, String topMenuNo, String loginMenuNo, String jobId, int personPermissionSet) {
		this.companyId = companyId;
		this.topMenuNo = topMenuNo;
		this.loginMenuNo = loginMenuNo;
		this.jobId = jobId;
		this.personPermissionSet = personPermissionSet;
	} 
	
	/**
	 * Creates the from java type.
	 *
	 * @param companyId the company id
	 * @param topMenuNo the top menu no
	 * @param loginMenuNo the login menu no
	 * @param jobId the user id
	 * @param personPermissionSet the layout id
	 */
	public static TopPageJobSet createFromJavaType(String companyId, String topMenuNo, String loginMenuNo, String jobId, int personPermissionSet) {
		return new TopPageJobSet(companyId, topMenuNo, loginMenuNo, jobId, personPermissionSet);
	}
}
