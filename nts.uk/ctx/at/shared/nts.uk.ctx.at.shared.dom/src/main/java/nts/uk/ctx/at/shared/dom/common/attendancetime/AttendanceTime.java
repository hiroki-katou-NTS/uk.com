/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.common.attendancetime;

import nts.arc.primitive.TimeClockPrimitiveValue;

/**
 * 勤怠時間.
 */
public class AttendanceTime extends TimeClockPrimitiveValue<AttendanceTime> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new attendance time.
	 *
	 * @param rawValue the raw value
	 */
	public AttendanceTime(Long rawValue) {
		super(rawValue);
	}

	/**
	 * Instantiates a new attendance time.
	 *
	 * @param hour the hour
	 * @param minute the minute
	 * @param second the second
	 */
	public AttendanceTime(int hour, int minute, int second) {
		super(hour, minute, second);
	}

}
