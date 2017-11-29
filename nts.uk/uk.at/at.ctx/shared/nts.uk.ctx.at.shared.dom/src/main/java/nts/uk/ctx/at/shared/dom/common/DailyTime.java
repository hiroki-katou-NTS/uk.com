/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.common;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

/**
 * The Class DailyTime.
 * 日単位
 */
@TimeRange(max = "24:00", min = "00:00")
public class DailyTime extends TimeDurationPrimitiveValue<DailyTime> {

	/**
	 * Instantiates a new daily time.
	 *
	 * @param timeAsMinutes the time as minutes
	 */
	public DailyTime(int timeAsMinutes) {
		super(timeAsMinutes);
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

}
