package nts.uk.ctx.pereg.app.find.person.setting.init;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pereg.dom.person.setting.init.PerInfoInitValueSetting;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PerInfoInitValueSettingDto {
	private String companyId;
	private String settingId;
	private String settingCode;
	private String settingName;
	
	public static PerInfoInitValueSettingDto fromDomain(PerInfoInitValueSetting domain) {
		return new PerInfoInitValueSettingDto(domain.getCompanyId(), domain.getInitValueSettingId(),
				domain.getSettingCode().v(), domain.getSettingName().v());
	}
}
