/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

/**
 * The Class RetentionYearsAmount.積立年休保持年数
 */
@IntegerMaxValue(99)
@IntegerMinValue(0)
public class RetentionYearsAmount extends IntegerPrimitiveValue<RetentionYearsAmount> {
	
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
