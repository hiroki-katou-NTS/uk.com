/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.common;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.statutory.worktime.week.WorkingTimeSetting;

/**
 * The Class WorkingTimeSettingDto.
 */

@Getter
@Setter
public class WorkingTimeSettingDto {

	/** The weekly time. */
	protected WeeklyUnitDto weeklyTime;

	/** The daily time. */
	protected DailyUnitDto dailyTime;

	/**
	 * From domain.
	 *
	 * @param domain the domain
	 * @return the working time setting dto
	 */
	public static WorkingTimeSettingDto fromDomain(WorkingTimeSetting domain) {
		WorkingTimeSettingDto dto = new WorkingTimeSettingDto();
		WeeklyUnitDto weeklyTime = new WeeklyUnitDto(domain.getWeeklyTime().getTime().v(), domain.getWeeklyTime().getStart().value);
		DailyUnitDto dailyTime = new DailyUnitDto(domain.getDailyTime().getDailyTime().v());
		dto.setWeeklyTime(weeklyTime);
		dto.setDailyTime(dailyTime);
		return dto;
	}
}
