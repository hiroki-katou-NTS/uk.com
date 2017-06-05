package nts.uk.ctx.sys.portal.dom.toppage.setting;

import lombok.EqualsAndHashCode;
import lombok.Value;
import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class TopPagePersonSetting.
 */
@EqualsAndHashCode(callSuper = false)
public class TopPagePersonSetting extends AggregateRoot {

	/** The company id. */
	private String cId;
	
	/** The top menu no. */
	private String topMenuNo;
	
	/** The login menu no. */
	private String loginMenuNo;
	
	/** The employee id. */
	private String sId;

	/**
	 * Instantiates a new top page title set.
	 *
	 * @param cId the company id
	 * @param topMenuNo the top menu no
	 * @param loginMenuNo the login menu no
	 * @param sId the employee id
	 */
	public TopPagePersonSetting(String cId, String topMenuNo, String loginMenuNo, String sId) {
		this.cId = cId;
		this.topMenuNo = topMenuNo;
		this.loginMenuNo = loginMenuNo;
		this.sId = sId;
	} 
	
	/**
	 * Creates the from java type.
	 *
	 * @param cId the company id
	 * @param topMenuNo the top menu no
	 * @param loginMenuNo the login menu no
	 * @param sId the employee id
	 */
	public static TopPagePersonSetting createFromJavaType(String cId, String topMenuNo, String loginMenuNo, String sId) {
		return new TopPagePersonSetting(cId, topMenuNo, loginMenuNo, sId);
	}
}
