/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew;

/**
 * The Interface WorkingTimeSettingNewSetMemento.
 */
public interface WorkingTimeSettingNewSetMemento {

	/**
	 * Sets the daily time.
	 *
	 * @param dailyTime the new daily time
	 */
	void setDailyTime(DailyTimeNew dailyTime);

	/**
	 * Sets the weekly time.
	 *
	 * @param weeklyTime the new weekly time
	 */
	void setWeeklyTime(WeeklyTimeNew weeklyTime);
}
