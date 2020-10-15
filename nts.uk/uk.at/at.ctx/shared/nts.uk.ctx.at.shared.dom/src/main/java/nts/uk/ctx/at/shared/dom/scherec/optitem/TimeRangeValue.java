/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.optitem;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

/**
 * The Class TimeRangeValue.
 */
// 時間範囲値
@TimeRange(min = "00:00", max = "744:00")
public class TimeRangeValue extends TimeDurationPrimitiveValue<TimeRangeValue> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new time range value.
	 *
	 * @param timeAsMinutes the time as minutes
	 */
	public TimeRangeValue(int timeAsMinutes) {
		super(timeAsMinutes);
	}

}
