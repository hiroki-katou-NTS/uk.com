package find.person.setting.user;

import lombok.Value;
import nts.uk.ctx.bs.person.dom.person.info.setting.user.UserSetting;

@Value
public class UserSettingDto {

	private int employeeCodeType;
	private int recentRegistrationType;
	private int cardNumberType;
	private String employeeCodeLetter;
	private String cardNumberLetter;

	public static UserSettingDto fromDomain(UserSetting domain) {

		return new UserSettingDto(domain.getEmpCodeValType().value, domain.getRecentRegType().value,
				domain.getCardNoValType().value, domain.getEmpCodeLetter(), domain.getCardNoLetter());

	}
}
