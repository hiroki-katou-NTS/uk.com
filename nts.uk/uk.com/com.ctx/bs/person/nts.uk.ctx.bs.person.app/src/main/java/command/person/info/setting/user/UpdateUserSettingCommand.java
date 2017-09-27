package command.person.info.setting.user;

import lombok.Value;

@Value
public class UpdateUserSettingCommand {
	private String employeeId;
	private int empCodeValType;
	private int cardNoValType;
	private int recentRegType;
	private String empCodeLetter;
	private String cardNoLetter;
}
