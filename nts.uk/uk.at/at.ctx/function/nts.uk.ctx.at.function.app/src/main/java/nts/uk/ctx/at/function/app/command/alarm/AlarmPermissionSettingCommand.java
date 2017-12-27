package nts.uk.ctx.at.function.app.command.alarm;

import java.util.List;

import lombok.Data;

@Data
public class AlarmPermissionSettingCommand {
	private boolean authSetting;
	private List<String> roleIds;
}
