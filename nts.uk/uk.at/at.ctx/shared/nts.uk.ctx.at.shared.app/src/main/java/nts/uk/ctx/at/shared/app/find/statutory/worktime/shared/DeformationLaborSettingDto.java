/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.shared;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.DeformationLaborSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.WeekStart;

/**
 * The Class DeformationLaborSettingDto.
 */
@Data
public class DeformationLaborSettingDto {

	/** The statutory setting. */
	private WorkingTimeSettingDto statutorySetting;

	/** The week start. */
	private int weekStart;

	/**
	 * From domain.
	 *
	 * @param domain the domain
	 * @return the deformation labor setting dto
	 */
	public static DeformationLaborSettingDto fromDomain(DeformationLaborSetting domain) {
		WorkingTimeSettingDto statutorySetting = WorkingTimeSettingDto.fromDomain(domain.getStatutorySetting());
		DeformationLaborSettingDto dto = new DeformationLaborSettingDto();
		dto.setStatutorySetting(statutorySetting);
		dto.setWeekStart(domain.getWeekStart().value);
		return dto;
	}

	/**
	 * To domain.
	 *
	 * @param dto the dto
	 * @return the deformation labor setting
	 */
	public static DeformationLaborSetting toDomain(DeformationLaborSettingDto dto) {
		DeformationLaborSetting domain = new DeformationLaborSetting();
		domain.setStatutorySetting(WorkingTimeSettingDto.toDomain(dto.getStatutorySetting()));
		domain.setWeekStart(WeekStart.valueOf(dto.getWeekStart()));
		return domain;
	}
}
