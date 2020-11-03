package nts.uk.ctx.at.function.app.command.alarm;

import lombok.Data;
import nts.uk.ctx.at.function.dom.alarm.AlarmPermissionSetting;

import java.util.List;

/**
 * The class Alarm permission setting command.<br>
 * Command アラームリスト権限設定
 */
@Data
public class AlarmPermissionSettingCommand {

	/**
	 * The Auth setting.
	 */
	private boolean authSetting;

	/**
	 * The Role ids.
	 */
	private List<String> roleIds;

	/**
	 * To domain.
	 *
	 * @param command the command
	 * @return the domain Alarm permission setting.
	 */
	public static AlarmPermissionSetting toDomain(AlarmPermissionSettingCommand command) {
		if (command == null) {
			return null;
		}
		return new AlarmPermissionSetting(command.isAuthSetting(), command.getRoleIds());
	}

}
