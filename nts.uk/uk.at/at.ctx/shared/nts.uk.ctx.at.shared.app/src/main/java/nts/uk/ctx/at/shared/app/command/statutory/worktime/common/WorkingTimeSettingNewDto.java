/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.common;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class WorkingTimeSettingNewDto.
 */
@Getter
@Setter
public class WorkingTimeSettingNewDto {

	/** The daily time. */
	/* 日単位. */
	private DailyTimeNewDto dailyTime;

	/** The weekly time. */
	/* 週間時間. */
	private WeeklyTimeNewDto weeklyTime;

}
