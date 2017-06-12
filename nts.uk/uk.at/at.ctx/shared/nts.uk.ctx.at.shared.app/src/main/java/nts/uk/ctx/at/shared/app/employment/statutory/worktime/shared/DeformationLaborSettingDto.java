/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.employment.statutory.worktime.shared;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared.DeformationLaborSetting;

/**
 * The Class DeformationLaborSettingDto.
 */
@Value
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
		return new DeformationLaborSettingDto(statutorySetting, domain.getWeekStart().value);
	}
}
