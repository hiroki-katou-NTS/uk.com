/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.employment.statutory.worktime.shared;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared.NormalSetting;

/**
 * The Class NormalSettingDto.
 */
@Value
public class NormalSettingDto {

	/** The statutory setting. */
	private WorkingTimeSettingDto statutorySetting;

	/** The week start. */
	private int weekStart;

	/**
	 * From domain.
	 *
	 * @param domain the domain
	 * @return the normal setting dto
	 */
	public static NormalSettingDto fromDomain(NormalSetting domain) {
		WorkingTimeSettingDto statutorySetting = WorkingTimeSettingDto.fromDomain(domain.getStatutorySetting());
		return new NormalSettingDto(statutorySetting, domain.getWeekStart().value);
	}
}
