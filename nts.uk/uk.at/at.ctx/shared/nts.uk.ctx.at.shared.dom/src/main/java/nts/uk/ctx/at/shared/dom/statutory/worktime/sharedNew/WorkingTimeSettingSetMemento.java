/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew;

/**
 * The Interface WorkingTimeSettingNewSetMemento.
 */
public interface WorkingTimeSettingSetMemento {

	/**
	 * Sets the daily time.
	 *
	 * @param dailyTime the new daily time
	 */
	void setDailyTime(DailyUnit dailyTime);

	/**
	 * Sets the weekly time.
	 *
	 * @param weeklyTime the new weekly time
	 */
	void setWeeklyTime(WeeklyUnit weeklyTime);
}
