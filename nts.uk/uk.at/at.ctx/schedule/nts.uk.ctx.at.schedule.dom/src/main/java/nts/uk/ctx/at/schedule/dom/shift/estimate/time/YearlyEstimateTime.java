/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.time;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * The Class YearlyEstimateTime.
 */
@IntegerRange(max = 59999, min = 0)
public class YearlyEstimateTime extends IntegerPrimitiveValue<YearlyEstimateTime>{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new yearly estimate time.
	 *
	 * @param rawValue the raw value
	 */
	public YearlyEstimateTime(Integer rawValue) {
		super(rawValue);
	}

}
