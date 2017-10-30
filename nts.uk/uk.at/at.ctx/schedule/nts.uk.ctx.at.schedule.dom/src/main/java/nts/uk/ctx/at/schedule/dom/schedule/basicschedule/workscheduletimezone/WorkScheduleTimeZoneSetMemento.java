/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletimezone;

import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Interface WorkScheduleTimeZoneSetMemento.
 */
public interface WorkScheduleTimeZoneSetMemento {

	/**
	 * Sets the schedule cnt.
	 *
	 * @param scheduleCnt the new schedule cnt
	 */
	public void setScheduleCnt(int scheduleCnt);
	
	
	/**
	 * Sets the schedule start clock.
	 *
	 * @param scheduleStartClock the new schedule start clock
	 */
	public void setScheduleStartClock(TimeWithDayAttr scheduleStartClock);
	
	
	/**
	 * Sets the schedule end clock.
	 *
	 * @param scheduleEndClock the new schedule end clock
	 */
	public void setScheduleEndClock(TimeWithDayAttr scheduleEndClock);
	
	
	/**
	 * Sets the bounce atr.
	 *
	 * @param bounceAtr the new bounce atr
	 */
	public void setBounceAtr(BounceAtr bounceAtr);
}
