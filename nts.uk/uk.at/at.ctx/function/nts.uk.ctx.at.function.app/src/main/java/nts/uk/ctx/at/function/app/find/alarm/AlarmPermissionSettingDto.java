package nts.uk.ctx.at.function.app.find.alarm;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.function.dom.alarm.AlarmPermissionSetting;

import java.util.List;

/**
 * The class Alarm permission setting dto.
 */
@Data
@AllArgsConstructor
public class AlarmPermissionSettingDto {

	/**
	 * The auth setting
	 */
	private boolean authSetting;

	/**
	 * The role id list
	 */
	private List<String> roleIds;

	/**
	 * Creates from domain.
	 *
	 * @param domain the domain
	 * @return the Alarm permission setting dto
	 */
	public static AlarmPermissionSettingDto createFromDomain(AlarmPermissionSetting domain) {
		if (domain == null) {
			return null;
		}
		return new AlarmPermissionSettingDto(domain.isAuthSetting(), domain.getRoleIds());
	}

}
