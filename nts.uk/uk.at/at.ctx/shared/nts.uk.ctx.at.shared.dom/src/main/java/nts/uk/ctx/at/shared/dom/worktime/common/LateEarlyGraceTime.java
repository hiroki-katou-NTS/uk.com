/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import nts.arc.primitive.TimeClockPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

/**
 * The Class LateEarlyGraceTime.
 */
@TimeRange(min = "00:00", max = "72:00")
//遅刻早退猶予時間
public class LateEarlyGraceTime extends TimeClockPrimitiveValue<LateEarlyGraceTime>{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new late early grace time.
	 *
	 * @param minutesFromZeroOClock the minutes from zero O clock
	 */
	public LateEarlyGraceTime(int minutesFromZeroOClock) {
		super(minutesFromZeroOClock);
	}

}
