/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.schedule.basicschedule;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.shr.com.enumcommon.DayAttr;
import nts.uk.shr.com.time.AttendanceClock;

/**
 * The Class ClockValue.
 */
@Getter
// 時刻(日区分付き)
public class ClockValue extends DomainObject{
	
	/** The attendance clock. */
	// 時刻 
	private AttendanceClock timeOfDay;
	
	/** The day attr. */
	// 日区分
	private DayAttr dayAtr;

	/**
	 * Instantiates a new clock value.
	 *
	 * @param timeOfDay the time of day
	 * @param dayAttr the day attr
	 */
	public ClockValue(AttendanceClock timeOfDay, DayAttr dayAtr) {
		this.timeOfDay = timeOfDay;
		this.dayAtr = dayAtr;
	}

	

}
