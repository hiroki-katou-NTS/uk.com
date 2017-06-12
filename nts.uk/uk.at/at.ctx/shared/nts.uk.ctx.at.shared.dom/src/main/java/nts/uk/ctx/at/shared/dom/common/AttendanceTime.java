/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.common;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

/**
 * 勤怠時間.
 */
// max value = 48 hours (2880 minutes).
// min value = 0 hours.
@IntegerMaxValue(2880)
@IntegerMinValue(0)
public class AttendanceTime extends IntegerPrimitiveValue<AttendanceTime> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new attendance time.
	 *
	 * @param rawValue the raw value
	 */
	public AttendanceTime(Integer rawValue) {
		super(rawValue);
	}

}
