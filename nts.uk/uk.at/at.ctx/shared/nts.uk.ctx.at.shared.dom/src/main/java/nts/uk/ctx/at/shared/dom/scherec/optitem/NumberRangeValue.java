/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.optitem;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerRange;

/**
 * The Class NumberRangeValue.
 */
// 回数範囲値
@HalfIntegerRange(min = -99999999.5, max = 99999999.5)
public class NumberRangeValue extends HalfIntegerPrimitiveValue<NumberRangeValue> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new number range value.
	 *
	 * @param rawValue the raw value
	 */
	public NumberRangeValue(Double rawValue) {
		super(rawValue);
	}

}
