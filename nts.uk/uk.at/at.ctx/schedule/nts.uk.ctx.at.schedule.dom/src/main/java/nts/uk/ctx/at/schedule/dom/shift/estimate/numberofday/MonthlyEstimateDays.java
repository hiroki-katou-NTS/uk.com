/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerRange;

/**
 * The Class MonthlyEstimateDays.
 */
// 月間目安日数
@HalfIntegerRange(min = 0, max = 31)
public class MonthlyEstimateDays extends HalfIntegerPrimitiveValue<MonthlyEstimateDays> {

	/**
	 * Instantiates a new monthly estimate days.
	 *
	 * @param rawValue the raw value
	 */
	public MonthlyEstimateDays(Double rawValue) {
		super(rawValue);
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

}
