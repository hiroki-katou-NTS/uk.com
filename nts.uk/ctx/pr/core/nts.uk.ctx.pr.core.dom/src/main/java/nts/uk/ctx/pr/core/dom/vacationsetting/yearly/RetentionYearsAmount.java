/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.vacationsetting.yearly;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

/**
 * The Class RetentionYearsAmount.
 */
@IntegerMaxValue(99)
@IntegerMinValue(0)
public class RetentionYearsAmount extends IntegerPrimitiveValue<RetentionYearsAmount>{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new retention years amount.
	 *
	 * @param rawValue the raw value
	 */
	public RetentionYearsAmount(Integer rawValue) {
		super(rawValue);
	}

}
