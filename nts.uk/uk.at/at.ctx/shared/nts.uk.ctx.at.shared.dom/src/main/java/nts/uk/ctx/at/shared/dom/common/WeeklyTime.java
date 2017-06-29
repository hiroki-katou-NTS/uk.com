/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.common;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;
import nts.gul.util.Time;

/**
 * 週間時間.
 */
@TimeRange(max = "168:00", min = "00:00")
public class WeeklyTime extends TimeDurationPrimitiveValue<WeeklyTime> {

	/** The Constant DEFAULT_VALUE. */
	public static final long DEFAULT_VALUE = 0L;

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new attendance time.
	 *
	 * @param hour the hour
	 * @param minute the minute
	 */
	public WeeklyTime(int hour, int minute) {
		super(hour, minute);
	}

	/**
	 * Instantiates a new attendance time.
	 *
	 * @param seconds the seconds
	 */
	public WeeklyTime(Long seconds) {
		super(seconds);
	}

	/**
	 * Instantiates a new weekly time.
	 *
	 * @param minutes the minutes
	 * @param isMinute the is minute
	 */
	public WeeklyTime(Long minutes, boolean isMinute) {
		super(minutes * Time.STEP);
	}

	/**
	 * Of minutes.
	 *
	 * @param minutes the minutes
	 * @return the weekly time
	 */
	public static WeeklyTime ofMinutes(Long minutes) {
		return new WeeklyTime(minutes, true);
	}

	/**
	 * Default value.
	 *
	 * @return the weekly time
	 */
	public static WeeklyTime defaultValue() {
		return new WeeklyTime(DEFAULT_VALUE);
	}

}
