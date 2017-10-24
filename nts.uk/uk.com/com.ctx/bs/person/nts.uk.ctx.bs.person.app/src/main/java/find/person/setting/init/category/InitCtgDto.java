package find.person.setting.init.category;

import lombok.Value;
import nts.uk.ctx.bs.person.dom.person.setting.init.category.InitValSettingCtg;

@Value
public class InitCtgDto {

	private String categoryCd;
	private String categoryName;

	public static InitCtgDto fromDomain(InitValSettingCtg domain) {
		return new InitCtgDto(domain.getCategoryCd(), domain.getCategoryName());
	}

}
