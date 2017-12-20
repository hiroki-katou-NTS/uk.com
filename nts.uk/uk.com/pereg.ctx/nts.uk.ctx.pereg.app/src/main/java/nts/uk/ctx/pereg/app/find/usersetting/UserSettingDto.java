package nts.uk.ctx.pereg.app.find.usersetting;

import lombok.Value;
import nts.uk.ctx.pereg.dom.usesetting.UserSetting;

@Value
public class UserSettingDto {

	private int employeeCodeType;
	private int recentRegistrationType;
	private int cardNumberType;
	private String employeeCodeLetter;
	private String cardNumberLetter;

	public static UserSettingDto fromDomain(UserSetting domain) {

		return new UserSettingDto(domain.getEmpCodeValType().value, domain.getRecentRegType().value,
				domain.getCardNoValType().value, domain.getEmpCodeLetter().v(), domain.getCardNoLetter().v());

	}
}
