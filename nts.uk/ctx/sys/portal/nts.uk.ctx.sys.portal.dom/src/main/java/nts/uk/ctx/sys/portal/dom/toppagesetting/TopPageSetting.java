package nts.uk.ctx.sys.portal.dom.toppagesetting;

import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class TopPageSetting.
 */
public class TopPageSetting extends AggregateRoot {
	
	/** The company id. */
	private String companyId;
	
	/** The category setting. */
	private String ctgSet;

	/**
	 * Instantiates a new top page setting.
	 *
	 * @param companyId the company id
	 * @param ctgSet the category setting
	 */
	public TopPageSetting(String companyId, String ctgSet) {
		this.companyId = companyId;
		this.ctgSet = ctgSet;
	}
	
	/**
	 * Creates the from java type.
	 *
	 * @param companyId the company id
	 * @param ctgSet the category setting
	 */
	public static TopPageSetting createFromJavaType(String companyId, String ctgSet) {
		return new TopPageSetting(companyId, ctgSet);
	}
}
