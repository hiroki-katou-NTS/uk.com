/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.common;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

/**
 * The Class WeeklyTime.
 */
// 週間時間
@TimeRange(max = "168:00", min = "00:00")
public class WeeklyTime extends TimeDurationPrimitiveValue<WeeklyTime> {

	/** The Constant DEFAULT_VALUE. */
	public static final int DEFAULT_VALUE = 0;

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new weekly time.
	 *
	 * @param timeAsMinutes
	 *            the time as minutes
	 */
	public WeeklyTime(int timeAsMinutes) {
		super(timeAsMinutes);
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
