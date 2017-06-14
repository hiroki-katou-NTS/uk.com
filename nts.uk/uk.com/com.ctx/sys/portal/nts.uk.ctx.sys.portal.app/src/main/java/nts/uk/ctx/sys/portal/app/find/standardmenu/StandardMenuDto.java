package nts.uk.ctx.sys.portal.app.find.standardmenu;

import lombok.Data;
import nts.uk.ctx.sys.portal.dom.standardmenu.StandardMenu;

/**
 * The Class StandardMenuDto.
 */
@Data
public class StandardMenuDto {
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
	 * From domain.
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
	public static StandardMenuDto fromDomain(StandardMenu standardMenu) {
		StandardMenuDto standardMenuDto = new StandardMenuDto();
		standardMenuDto.companyId = standardMenu.getCompanyId();
		standardMenuDto.url = standardMenu.getUrl();
		standardMenuDto.webMenuSettingDisplayIndicator = standardMenu.getWebMenuSettingDisplayIndicator();
		standardMenuDto.code = standardMenu.getCode();
		standardMenuDto.system = standardMenu.getSystem();
		standardMenuDto.classification = standardMenu.getClassification();
		standardMenuDto.afterLoginDisplayIndicator = standardMenu.getAfterLoginDisplayIndicator();
		standardMenuDto.logSettingDisplayIndicator = standardMenu.getLogSettingDisplayIndicator();
		standardMenuDto.targetItems = standardMenu.getTargetItems();
		standardMenuDto.displayName = standardMenu.getDisplayName();
		return standardMenuDto;
	}
}
