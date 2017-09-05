/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.time;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

/**
 * The Class MonthlyEstimateTime.
 */
@TimeRange(max = "999:59", min = "00:00")
public class MonthlyEstimateTime extends TimeDurationPrimitiveValue<MonthlyEstimateTime> {

	/**
	 * Instantiates a new monthly estimate time.
	 *
	 * @param timeAsMinutes the time as minutes
	 */
	public MonthlyEstimateTime(int timeAsMinutes) {
		super(timeAsMinutes);
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

}
