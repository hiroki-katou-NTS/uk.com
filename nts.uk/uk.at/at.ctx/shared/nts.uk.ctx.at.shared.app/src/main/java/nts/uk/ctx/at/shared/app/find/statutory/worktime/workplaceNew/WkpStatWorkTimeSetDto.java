/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.workplaceNew;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.common.WorkingTimeSettingDto;

/**
 * The Class ComStatWorkTimeSetDto.
 */
@Getter
@Setter
@Builder
public class WkpStatWorkTimeSetDto {
	
	/** The year. */
	private int year;
	
	/** The employee id. */
	private String workplaceId;

	/** The defor labor setting. */
	private WkpDeforLaborSettingDto deforLaborSetting;

	/** The flex setting. */
	private WkpFlexSettingDto flexSetting;

	/** The normal setting. */
	private WkpNormalSettingDto normalSetting;

	/** The regular labor time. */
	private WorkingTimeSettingDto regularLaborTime;

	/** The trans labor time. */
	private WorkingTimeSettingDto transLaborTime;

}
