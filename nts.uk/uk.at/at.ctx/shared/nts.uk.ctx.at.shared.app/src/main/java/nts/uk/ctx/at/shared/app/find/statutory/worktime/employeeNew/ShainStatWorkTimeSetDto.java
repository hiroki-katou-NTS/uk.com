/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.employeeNew;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * The Class ComStatWorkTimeSetDto.
 */
@Getter
@Setter
@Builder
public class ShainStatWorkTimeSetDto {

	/** The defor labor setting. */
	private ShainDeforLaborSettingDto deforLaborSetting;

	/** The flex setting. */
	private ShainFlexSettingDto flexSetting;

	/** The normal setting. */
	private ShainNormalSettingDto normalSetting;

	/** The regular labor time. */
	private ShainRegularWorkHourDto regularLaborTime;

	/** The trans labor time. */
	private ShainSpeDeforLaborHourDto speDeforLaborSetting;

}
