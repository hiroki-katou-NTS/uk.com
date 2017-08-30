/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.time;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;
import nts.gul.util.Time;

/**
 * The Class YearlyEstimateTime.
 */
@TimeRange(max = "9999:59", min = "00:00")
public class YearlyEstimateTime extends TimeDurationPrimitiveValue<YearlyEstimateTime> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new yearly estimate time.
	 *
	 * @param rawValue the raw value
	 */
	public YearlyEstimateTime(Long rawValue) {
		super(rawValue);
	}

	/**
	 * Of minutes.
	 *
	 * @param minutes the minutes
	 * @return the yearly estimate time
	 */
	public static YearlyEstimateTime ofMinutes(int minutes) {
		return new YearlyEstimateTime(minutes * Time.STEP);
	}

}
