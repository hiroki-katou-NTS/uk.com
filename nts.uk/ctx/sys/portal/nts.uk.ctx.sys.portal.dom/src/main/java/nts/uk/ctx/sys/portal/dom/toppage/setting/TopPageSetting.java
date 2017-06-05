package nts.uk.ctx.sys.portal.dom.toppage.setting;

import lombok.EqualsAndHashCode;
import lombok.Value;
import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class TopPageSetting.
 */
@EqualsAndHashCode(callSuper = false)
public class TopPageSetting extends AggregateRoot {
	
	/** The company id. */
	private String cId;
	
	/** The category setting. */
	private String ctgSet;

	/**
	 * Instantiates a new top page setting.
	 *
	 * @param cId the company id
	 * @param ctgSet the category setting
	 */
	public TopPageSetting(String cId, String ctgSet) {
		this.cId = cId;
		this.ctgSet = ctgSet;
	}
	
	/**
	 * Creates the from java type.
	 *
	 * @param cId the company id
	 * @param ctgSet the category setting
	 */
	public static TopPageSetting createFromJavaType(String cId, String ctgSet) {
		return new TopPageSetting(cId, ctgSet);
	}
}
