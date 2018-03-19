/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * The Class WorkingTimeSettingDto.
 */
@Getter
@Setter
@Builder
public class WorkingTimeSettingDto {

	/** The weekly time. */
	private WeeklyUnitDto weeklyTime;

	/** The daily time. */
	private DailyUnitDto dailyTime;

}
