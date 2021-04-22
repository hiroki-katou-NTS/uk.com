package nts.uk.ctx.at.function.app.command.alarmworkplace.checkcondition;

import lombok.Data;

import java.util.List;

@Data
public class AlarmPermissionSettingCommand {

    private int authSetting;

    private List<String> roleIds;
}
