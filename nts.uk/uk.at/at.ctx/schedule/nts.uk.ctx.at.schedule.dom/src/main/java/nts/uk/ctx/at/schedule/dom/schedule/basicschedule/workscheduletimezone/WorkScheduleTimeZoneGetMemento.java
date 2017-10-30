/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletimezone;

import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Interface WorkScheduleTimeZoneGetMemento.
 */
public interface WorkScheduleTimeZoneGetMemento {
	
	/**
	 * Gets the schedule cnt.
	 *
	 * @return the schedule cnt
	 */
	public int getScheduleCnt();
	
	
	/**
	 * Gets the schedule start clock.
	 *
	 * @return the schedule start clock
	 */
	public TimeWithDayAttr getScheduleStartClock();
	
	
	/**
	 * Gets the bounce atr.
	 *
	 * @return the bounce atr
	 */
	public BounceAtr getBounceAtr();
	
	
	/**
	 * Gets the schedule end clock.
	 *
	 * @return the schedule end clock
	 */
	public TimeWithDayAttr getScheduleEndClock();

}
