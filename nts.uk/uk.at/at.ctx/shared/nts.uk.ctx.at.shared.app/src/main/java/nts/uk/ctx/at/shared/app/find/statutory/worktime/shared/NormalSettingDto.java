/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.shared;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.NormalSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.WeekStart;

/**
 * The Class NormalSettingDto.
 */

/**
 * Instantiates a new normal setting dto.
 */
@Data
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
		NormalSettingDto dto = new NormalSettingDto();
		dto.setStatutorySetting(statutorySetting);
		dto.setWeekStart(domain.getWeekStart().value);
		return dto;
	}

	/**
	 * To domain.
	 *
	 * @param dto the dto
	 * @return the normal setting
	 */
	public static NormalSetting toDomain(NormalSettingDto dto) {
		NormalSetting domain = new NormalSetting();
		domain.setStatutorySetting(WorkingTimeSettingDto.toDomain(dto.getStatutorySetting()));
		domain.setWeekStart(WeekStart.valueOf(dto.getWeekStart()));
		return domain;
	}
}
