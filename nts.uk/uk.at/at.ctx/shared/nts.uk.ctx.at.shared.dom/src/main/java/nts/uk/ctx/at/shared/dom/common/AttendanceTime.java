/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.common;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

/**
 * 勤怠時間.
 */
@TimeRange(max = "48:00", min = "00:00")
public class AttendanceTime extends TimeDurationPrimitiveValue<AttendanceTime> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new attendance time.
	 *
	 * @param hour the hour
	 * @param minute the minute
	 */
	public AttendanceTime(int hour, int minute) {
		super(hour, minute);
	}

	/**
	 * Instantiates a new attendance time.
	 *
	 * @param seconds the seconds
	 */
	public AttendanceTime(Long seconds) {
		super(seconds);
	}

}
