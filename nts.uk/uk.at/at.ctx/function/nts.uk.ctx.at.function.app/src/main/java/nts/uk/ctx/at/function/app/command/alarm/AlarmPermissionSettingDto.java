package nts.uk.ctx.at.function.app.command.alarm;

import java.util.List;

import lombok.Data;

@Data
public class AlarmPermissionSettingDto {
	private boolean authSetting;
	private List<String> roleIds;
}
