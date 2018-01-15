/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.personallaborcondition;

import nts.arc.primitive.TimeClockPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

/**
 * The Class AttendanceTime.
 */
//勤怠時間
@TimeRange(min = "0:00", max = "48:00")
public class AttendanceTime extends TimeClockPrimitiveValue<AttendanceTime>  {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new attendance time.
	 *
	 * @param minutesFromZeroOClock the minutes from zero O clock
	 */
	public AttendanceTime(int minutesFromZeroOClock) {
		super(minutesFromZeroOClock);
	}

}
