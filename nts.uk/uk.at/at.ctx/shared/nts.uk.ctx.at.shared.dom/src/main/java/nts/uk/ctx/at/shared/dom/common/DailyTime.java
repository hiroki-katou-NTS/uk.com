/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.common;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;
import nts.gul.util.Time;

/**
 * The Class DailyTime.
 */
@TimeRange(max = "24:00", min = "00:00")
public class DailyTime extends TimeDurationPrimitiveValue<DailyTime> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new daily time.
	 *
	 * @param hour the hour
	 * @param minute the minute
	 */
	public DailyTime(int hour, int minute) {
		super(hour, minute);
	}

	/**
	 * Instantiates a new daily time.
	 *
	 * @param seconds the seconds
	 */
	public DailyTime(Long seconds) {
		super(seconds);
	}

	/**
	 * Instantiates a new daily time.
	 *
	 * @param minutes the minutes
	 * @param isMinute the is minute
	 */
	public DailyTime(Long minutes, boolean isMinute) {
		super(minutes * Time.STEP);
	}

	/**
	 * Of minutes.
	 *
	 * @param minutes the minutes
	 * @return the daily time
	 */
	public static DailyTime ofMinutes(Long minutes) {
		return new DailyTime(minutes, true);
	}

}
