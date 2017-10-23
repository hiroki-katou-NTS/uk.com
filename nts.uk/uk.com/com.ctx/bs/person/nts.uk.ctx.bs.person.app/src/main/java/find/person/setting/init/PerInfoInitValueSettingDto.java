package find.person.setting.init;

import lombok.Value;
import nts.uk.ctx.bs.person.dom.person.setting.init.PerInfoInitValueSetting;

@Value
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
