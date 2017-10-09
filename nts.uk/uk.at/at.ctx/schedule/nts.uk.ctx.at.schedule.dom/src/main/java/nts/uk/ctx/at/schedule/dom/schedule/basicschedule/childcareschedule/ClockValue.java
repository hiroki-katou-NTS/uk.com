/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.schedule.basicschedule.childcareschedule;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

/**
 * The Class ClockValue.
 */
// 時刻(日区分付き)
@TimeRange(min = "-12:00", max = "71:59")
public class ClockValue extends TimeDurationPrimitiveValue<ClockValue>{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new clock value.
	 *
	 * @param timeAsMinutes the time as minutes
	 */
	public ClockValue(int timeAsMinutes) {
		super(timeAsMinutes);
	}


}
