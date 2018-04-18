/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew;

/**
 * The Interface WorkingTimeSettingNewGetMemento.
 */
public interface WorkingTimeSettingGetMemento {

	/**
	 * Gets the daily time new.
	 *
	 * @return the daily time new
	 */
	DailyUnit getDailyTime();

	/**
	 * Gets the weekly time new.
	 *
	 * @return the weekly time new
	 */
	WeeklyUnit getWeeklyTime();
}
