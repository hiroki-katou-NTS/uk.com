/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew;

import nts.uk.ctx.at.shared.dom.common.WeeklyTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.WeekStart;

/**
 * The Interface WeeklyTimeNewSetMemento.
 */
public interface WeeklyTimeNewSetMemento {

	/**
	 * Sets the weekly time.
	 *
	 * @param weeklyTime the new weekly time
	 */
	void setWeeklyTime(WeeklyTime weeklyTime);

	/**
	 * Sets the week start.
	 *
	 * @param weekStart the new week start
	 */
	void setWeekStart(WeekStart weekStart);
}
