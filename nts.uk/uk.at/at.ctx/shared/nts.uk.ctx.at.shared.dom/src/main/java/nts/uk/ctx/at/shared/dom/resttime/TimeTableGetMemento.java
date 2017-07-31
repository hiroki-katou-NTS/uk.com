/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.resttime;

import nts.uk.ctx.at.shared.dom.worktimeset.TimeDayAtr;

/**
 * The Interface TimeTableGetMemento.
 */
public interface TimeTableGetMemento {

	/**
	 * Gets the start day.
	 *
	 * @return the start day
	 */
	public TimeDayAtr getStartDay();

	/**
	 * Gets the start time.
	 *
	 * @return the start time
	 */
	public Integer getStartTime();

	/**
	 * Gets the end day.
	 *
	 * @return the end day
	 */
	public TimeDayAtr getEndDay();

	/**
	 * Gets the end time.
	 *
	 * @return the end time
	 */
	public Integer getEndTime();
}
