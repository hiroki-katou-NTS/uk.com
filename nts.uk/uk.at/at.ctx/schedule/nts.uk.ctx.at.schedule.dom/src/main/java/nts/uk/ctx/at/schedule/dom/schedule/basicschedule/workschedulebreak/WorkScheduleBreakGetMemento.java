/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workschedulebreak;

import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Interface WorkScheduleBreakGetMemento.
 */
public interface WorkScheduleBreakGetMemento {

	/**
	 * Gets the schedule break cnt.
	 *
	 * @return the schedule break cnt
	 */
	public ScheduledBreakCnt getScheduleBreakCnt();
	
	
	/**
	 * Gets the scheduled start clock.
	 *
	 * @return the scheduled start clock
	 */
	public TimeWithDayAttr getScheduledStartClock();
	
	
	/**
	 * Gets the scheduled end clock.
	 *
	 * @return the scheduled end clock
	 */
	public TimeWithDayAttr getScheduledEndClock();
}
