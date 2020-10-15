/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.optitem;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * The Class AmountRangeValue.
 */
// 金額範囲値
@IntegerRange(min = -99999999, max = 999999999)
public class AmountRangeValue extends IntegerPrimitiveValue<AmountRangeValue> {
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new amount range value.
	 *
	 * @param rawValue the raw value
	 */
	public AmountRangeValue(Integer rawValue) {
		super(rawValue);
	}

}
