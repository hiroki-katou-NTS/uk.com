package nts.uk.ctx.at.function.app.find.alarmworkplace;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class WkpAlarmPermissionSettingDto {
	private boolean authSetting;
	private List<String> roleIds;
}
