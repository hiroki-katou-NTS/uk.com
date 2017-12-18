/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

import nts.arc.primitive.TimeClockPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

/**
 * The Class ReflectReferenceTwoWorkTime.
 */
// ２回目勤務の反映基準時間
@TimeRange(min = "00:00", max = "72:00")
public class ReflectReferenceTwoWorkTime extends TimeClockPrimitiveValue<ReflectReferenceTwoWorkTime> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new reflect reference two work time.
	 *
	 * @param minutesFromZeroOClock the minutes from zero O clock
	 */
	public ReflectReferenceTwoWorkTime(int minutesFromZeroOClock) {
		super(minutesFromZeroOClock);
	}

}
