package nts.uk.ctx.pereg.app.find.person.setting.init;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pereg.dom.person.setting.init.category.PerInfoInitValueSettingCtg;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PerInitValueSettingDto {

	private String settingId;

	private String settingCode;

	private String settingName;

	private List<PerInfoInitValueSettingCtg> ctgList;

	public static PerInitValueSettingDto fromDomain(PerInfoInitValueSettingDto settingDto,
			List<PerInfoInitValueSettingCtg> ctgLst) {

		return new PerInitValueSettingDto(settingDto.getSettingId(), settingDto.getSettingCode(),
				settingDto.getSettingName(), ctgLst);
	}

}
