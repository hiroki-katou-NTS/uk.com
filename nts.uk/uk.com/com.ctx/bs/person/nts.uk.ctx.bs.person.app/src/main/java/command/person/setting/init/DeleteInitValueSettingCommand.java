package command.person.setting.init;

import lombok.Value;

@Value
public class DeleteInitValueSettingCommand {

	private String settingId;

	private String settingCode;

}
