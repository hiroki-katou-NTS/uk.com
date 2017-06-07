package nts.uk.ctx.sys.portal.dom.standardmenu;

import lombok.Value;
import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class StandardMenu.
 */
@Value
public class StandardMenu extends AggregateRoot {
	/** The company id. */
	private String companyId;
	
	/** The Url. */
	private String url;
	
	/** The Web Menu Setting Display Indicator. */
	private int webMenuSettingDisplayIndicator;
	
	/** The menu code. */
	private String code;
	
	/** The system. */
	private String system;
	
	/** The classification. */
	private String classification;
	
	/** The After Login Display Indicator. */
	private int afterLoginDisplayIndicator;
	
	/** The Log Setting Display Indicator. */
	private int logSettingDisplayIndicator;

	/** The Target Items. */
	private String targetItems;
	
	/** The Display Name. */
	private String displayName;

	/**
	 * Instantiates a new Standard Menu.
	 *
	 * @param companyId the company Id
	 * @param url the url
	 * @param webMenuSettingDisplayIndicator the webMenuSettingDisplayIndicator
	 * @param code the menu code
	 * @param system the system
	 * @param classification the classification
	 * @param afterLoginDisplayIndicator the afterLoginDisplayIndicator
	 * @param logSettingDisplayIndicator the logSettingDisplayIndicator
	 * @param targetItems the target items
	 * @param displayName the display name
	 */
	public StandardMenu(String companyId, String url, int webMenuSettingDisplayIndicator, String code, String system,
			String classification, int afterLoginDisplayIndicator, int logSettingDisplayIndicator, String targetItems,
			String displayName) {
		this.companyId = companyId;
		this.url = url;
		this.webMenuSettingDisplayIndicator = webMenuSettingDisplayIndicator;
		this.code = code;
		this.system = system;
		this.classification = classification;
		this.afterLoginDisplayIndicator = afterLoginDisplayIndicator;
		this.logSettingDisplayIndicator = logSettingDisplayIndicator;
		this.targetItems = targetItems;
		this.displayName = displayName;
	}
	
	/**
	 * Creates the from java type.
	 *
	 * @param companyId the company Id
	 * @param url the url
	 * @param webMenuSettingDisplayIndicator the webMenuSettingDisplayIndicator
	 * @param code the menu code
	 * @param system the system
	 * @param classification the classification
	 * @param afterLoginDisplayIndicator the afterLoginDisplayIndicator
	 * @param logSettingDisplayIndicator the logSettingDisplayIndicator
	 * @param targetItems the target items
	 * @param displayName the display name
	 */
	public static StandardMenu createFromJavaType(String companyId, String url, int webMenuSettingDisplayIndicator, String code, String system,
			String classification, int afterLoginDisplayIndicator, int logSettingDisplayIndicator, String targetItems,
			String displayName) {
		return new StandardMenu(companyId, url, webMenuSettingDisplayIndicator, code, system,
				classification, afterLoginDisplayIndicator, logSettingDisplayIndicator, targetItems,
				displayName);
	}
}
