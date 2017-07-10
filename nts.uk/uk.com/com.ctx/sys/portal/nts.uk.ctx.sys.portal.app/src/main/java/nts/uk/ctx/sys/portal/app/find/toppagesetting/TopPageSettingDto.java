package nts.uk.ctx.sys.portal.app.find.toppagesetting;

import lombok.Value;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPageSetting;

/**
 * 
 * @author sonnh1
 *
 */
@Value
public class TopPageSettingDto {
	// Category Setting
	int ctgSet;

	public static TopPageSettingDto fromDomain(TopPageSetting domain) {
		return new TopPageSettingDto(domain.getCategorySet().value);
	}
}
