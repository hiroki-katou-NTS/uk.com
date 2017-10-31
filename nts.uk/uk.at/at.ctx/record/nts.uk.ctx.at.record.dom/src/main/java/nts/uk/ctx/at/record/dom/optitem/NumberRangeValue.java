/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.optitem;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalRange;

/**
 * The Class NumberRangeValue.
 */
// 回数範囲値
@DecimalRange(min = "-99999999", max = "999999999")
public class NumberRangeValue extends DecimalPrimitiveValue<NumberRangeValue> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new number range value.
	 *
	 * @param rawValue the raw value
	 */
	public NumberRangeValue(BigDecimal rawValue) {
		super(rawValue);
	}

}
