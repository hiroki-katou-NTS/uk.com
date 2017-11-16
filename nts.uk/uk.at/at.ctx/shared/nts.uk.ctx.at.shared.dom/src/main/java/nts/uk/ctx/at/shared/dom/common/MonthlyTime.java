/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.common;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

/**
 * The Class MonthlyTime.
 */
@TimeRange(max = "744:00", min = "00:00")
public class MonthlyTime extends TimeDurationPrimitiveValue<MonthlyTime> {

	/**
	 * Instantiates a new monthly time.
	 *
	 * @param timeAsMinutes the time as minutes
	 */
	public MonthlyTime(int timeAsMinutes) {
		super(timeAsMinutes);
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

}
