/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.workplaceNew;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * The Class ComStatWorkTimeSetDto.
 */
@Getter
@Setter
@Builder
public class WkpStatWorkTimeSetDto {

	/** The defor labor setting. */
	private WkpDeforLaborSettingDto deforLaborSetting;

	/** The flex setting. */
	private WkpFlexSettingDto flexSetting;

	/** The normal setting. */
	private WkpNormalSettingDto normalSetting;

	/** The regular labor time. */
	private WkpRegularWorkHourDto regularLaborTime;

	/** The trans labor time. */
	private WkpTransLaborHourDto transLaborTime;

}
