/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.numberofday;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * The Class YearlyEstimateDays.
 */
// 年間目安日数
@IntegerRange(min = 0 , max = 366)
public class YearlyEstimateDays extends IntegerPrimitiveValue<YearlyEstimateDays>{

	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new yearly estimate days.
	 *
	 * @param rawValue the raw value
	 */
	public YearlyEstimateDays(Integer rawValue) {
		super(rawValue);
	}


}
