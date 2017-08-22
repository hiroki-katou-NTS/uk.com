/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.time;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * The Class MonthlyEstimateTime.
 */
@IntegerRange(min = 0, max = 5999)
public class MonthlyEstimateTime extends IntegerPrimitiveValue<MonthlyEstimateTime>{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new monthly estimate time.
	 *
	 * @param rawValue the raw value
	 */
	public MonthlyEstimateTime(Integer rawValue) {
		super(rawValue);
	}

}
