/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.time;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;
import nts.gul.util.Time;

/**
 * The Class MonthlyEstimateTime.
 */
@TimeRange(max = "999:59", min = "00:00")
public class MonthlyEstimateTime extends TimeDurationPrimitiveValue<MonthlyEstimateTime> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new monthly estimate time.
	 *
	 * @param rawValue the raw value
	 */
	public MonthlyEstimateTime(Long rawValue) {
		super(rawValue);
	}

	/**
	 * Of minutes.
	 *
	 * @param minutes the minutes
	 * @return the monthly estimate time
	 */
	public static MonthlyEstimateTime ofMinutes(int minutes) {
		return new MonthlyEstimateTime(minutes * Time.STEP);
	}

}
