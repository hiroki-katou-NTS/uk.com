package nts.uk.ctx.pereg.app.find.person.setting.init.category;

import lombok.Value;
import nts.uk.ctx.pereg.dom.person.setting.init.category.InitValSettingCtg;


/**
 * @author sonnlb
 *
 */
@Value
public class SettingCtgDto {

	private String categoryCd;
	private String categoryName;

	public static SettingCtgDto fromDomain(InitValSettingCtg domain) {
		return new SettingCtgDto(domain.getCategoryCd(), domain.getCategoryName());
	}

}
