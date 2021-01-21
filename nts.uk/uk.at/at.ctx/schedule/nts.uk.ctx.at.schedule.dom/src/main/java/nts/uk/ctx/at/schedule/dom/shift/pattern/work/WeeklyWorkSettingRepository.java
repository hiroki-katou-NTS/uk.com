/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.pattern.work;

import nts.uk.ctx.at.schedule.dom.shift.weeklywrkday.WeeklyWorkDayPattern;

import java.util.List;
import java.util.Optional;

public interface WeeklyWorkSettingRepository {

	/**
	 * Find by id.
	 *
	 * @param companyId the company id
	 * @param dayOfWeek the day of week
	 * @return the optional
	 */
	public Optional<WeeklyWorkSetting> findById(String companyId, int dayOfWeek);
	
	
	/**
	 * Find all.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	public List<WeeklyWorkSetting> findAll(String companyId);

	/**
	 * get all weekly work day
	 * @param companyId
	 * @return
	 */
	WeeklyWorkDayPattern getWeeklyWorkDayPatternByCompanyId(String companyId);

	/**
	 * insert weekly work day
	 */
	void addWeeklyWorkDayPattern(WeeklyWorkDayPattern weeklyWorkDayPattern);

	/**
	 * update weekly work day
	 */
	void updateWeeklyWorkDayPattern(WeeklyWorkDayPattern weeklyWorkDayPattern);
}
