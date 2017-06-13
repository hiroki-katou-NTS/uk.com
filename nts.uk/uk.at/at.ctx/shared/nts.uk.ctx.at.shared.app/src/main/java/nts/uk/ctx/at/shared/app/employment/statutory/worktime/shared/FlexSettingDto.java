/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.employment.statutory.worktime.shared;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared.FlexSetting;

/**
 * The Class FlexSettingDto.
 */
@Value
public class FlexSettingDto {

	/** The specified setting. */
	private WorkingTimeSettingDto specifiedSetting;

	/** The statutory setting. */
	private WorkingTimeSettingDto statutorySetting;

	/**
	 * From domain.
	 *
	 * @param domain the domain
	 * @return the flex setting dto
	 */
	public static FlexSettingDto fromDomain(FlexSetting domain) {
		WorkingTimeSettingDto specifiedSetting = WorkingTimeSettingDto.fromDomain(domain.getSpecifiedSetting());
		WorkingTimeSettingDto statutorySetting = WorkingTimeSettingDto.fromDomain(domain.getStatutorySetting());
		return new FlexSettingDto(specifiedSetting, statutorySetting);
	}
}
