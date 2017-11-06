/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerRange;

/**
 * The Class YearlyEstimateDays.
 */
// 年間目安日数
@HalfIntegerRange(min = 0, max = 366)
public class YearlyEstimateDays extends HalfIntegerPrimitiveValue<YearlyEstimateDays> {

	/**
	 * Instantiates a new yearly estimate days.
	 *
	 * @param rawValue the raw value
	 */
	public YearlyEstimateDays(Double rawValue) {
		super(rawValue);
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

}
