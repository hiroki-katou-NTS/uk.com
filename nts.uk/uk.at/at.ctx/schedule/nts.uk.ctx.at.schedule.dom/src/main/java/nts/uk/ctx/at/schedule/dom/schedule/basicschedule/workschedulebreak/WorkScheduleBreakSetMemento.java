/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workschedulebreak;

import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Interface WorkScheduleBreakSetMemento.
 */
public interface WorkScheduleBreakSetMemento {
	
	/**
	 * Sets the scheduled break cnt.
	 *
	 * @param scheduleBreakCnt the new scheduled break cnt
	 */
	public void setScheduledBreakCnt(ScheduledBreakCnt scheduleBreakCnt);
	
	
	/**
	 * Sets the scheduled start clock.
	 *
	 * @param scheduledStartClock the new scheduled start clock
	 */
	public void setScheduledStartClock(TimeWithDayAttr  scheduledStartClock);
	
	
	/**
	 * Sets the scheduled end clock.
	 *
	 * @param scheduledEndClock the new scheduled end clock
	 */
	public void setScheduledEndClock(TimeWithDayAttr scheduledEndClock);

}
