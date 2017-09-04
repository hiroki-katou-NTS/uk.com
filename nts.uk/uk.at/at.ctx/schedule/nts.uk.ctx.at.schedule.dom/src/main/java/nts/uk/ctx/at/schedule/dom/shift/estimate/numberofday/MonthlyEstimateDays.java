/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * The Class MonthlyEstimateDays.
 */
// 月間目安日数
@IntegerRange(min = 0, max = 31)
public class MonthlyEstimateDays extends IntegerPrimitiveValue<MonthlyEstimateDays> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new monthly estimate days.
	 *
	 * @param rawValue
	 *            the raw value
	 */
	public MonthlyEstimateDays(Integer rawValue) {
		super(rawValue);
	}

}
