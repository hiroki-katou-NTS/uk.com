/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.time;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

/**
 * The Class YearlyEstimateTime.
 */
@TimeRange(max = "9999:59", min = "00:00")
public class YearlyEstimateTime extends TimeDurationPrimitiveValue<YearlyEstimateTime> {

	/**
	 * Instantiates a new yearly estimate time.
	 *
	 * @param timeAsMinutes the time as minutes
	 */
	public YearlyEstimateTime(int timeAsMinutes) {
		super(timeAsMinutes);
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

}
