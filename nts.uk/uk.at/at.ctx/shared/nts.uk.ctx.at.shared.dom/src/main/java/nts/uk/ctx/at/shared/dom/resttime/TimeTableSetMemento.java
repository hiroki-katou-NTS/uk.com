/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.resttime;

import nts.uk.ctx.at.shared.dom.worktimeset.TimeDayAtr;

/**
 * The Interface TimeTableSetMemento.
 */
public interface TimeTableSetMemento {

	/**
	 * Sets the start day.
	 *
	 * @param startDay the new start day
	 */
	public void setStartDay(TimeDayAtr startDay);

	/**
	 * Sets the start time.
	 *
	 * @param startTime the new start time
	 */
	public void setStartTime(Integer startTime);

	/**
	 * Sets the end day.
	 *
	 * @param endDay the new end day
	 */
	public void setEndDay(TimeDayAtr endDay);

	/**
	 * Sets the end time.
	 *
	 * @param endTime the new end time
	 */
	public void setEndTime(Integer endTime);
}
