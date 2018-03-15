/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.common;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

/**
 * The Class MonthlyEstimateTime.
 */
// 月間時間
@TimeRange(max = "744:00", min = "00:00")
public class MonthlyEstimateTime extends TimeDurationPrimitiveValue<MonthlyEstimateTime> {

	/**
	 * Instantiates a new monthly estimate time.
	 *
	 * @param timeAsMinutes
	 *            the time as minutes
	 */
	public MonthlyEstimateTime(int timeAsMinutes) {
		super(timeAsMinutes);
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

}
