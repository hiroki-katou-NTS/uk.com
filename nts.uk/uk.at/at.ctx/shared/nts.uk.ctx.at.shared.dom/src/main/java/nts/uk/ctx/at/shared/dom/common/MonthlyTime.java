/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.common;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;
import nts.gul.util.Time;

/**
 * The Class MonthlyTime.
 */
@TimeRange(max = "744:00", min = "00:00")
public class MonthlyTime extends TimeDurationPrimitiveValue<MonthlyTime> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new monthly time.
	 *
	 * @param hour the hour
	 * @param minute the minute
	 */
	public MonthlyTime(int hour, int minute) {
		super(hour, minute);
	}

	/**
	 * Instantiates a new monthly time.
	 *
	 * @param seconds the seconds
	 */
	public MonthlyTime(Long seconds) {
		super(seconds);
	}

	/**
	 * Instantiates a new monthly time.
	 *
	 * @param minutes the minutes
	 * @param isMinute the is minute
	 */
	public MonthlyTime(Long minutes, boolean isMinute) {
		super(minutes * Time.STEP);
	}

	/**
	 * Of minutes.
	 *
	 * @param minutes the minutes
	 * @return the monthly time
	 */
	public static MonthlyTime ofMinutes(Long minutes) {
		return new MonthlyTime(minutes, true);
	}

}
