package nts.uk.ctx.pereg.app.find.person.setting.init.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.pereg.dom.person.setting.init.category.PerInfoInitValueSettingCtg;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PerInfoInitValueSettingCtgDto {
	private String perInfoCtgId;
	private String categoryName;
	private boolean isSetting;

	public static PerInfoInitValueSettingCtgDto fromDomain(PerInfoInitValueSettingCtg domain) {
		return new PerInfoInitValueSettingCtgDto(domain.getPerInfoCtgId(), domain.getCategoryName(),
				domain.isSetting());
	}
}
