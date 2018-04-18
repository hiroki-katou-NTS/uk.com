/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.common;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * The Class Year.
 */
// 年
@IntegerRange(min = 1, max = 9999)
public class Year extends IntegerPrimitiveValue<Year> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new year.
	 *
	 * @param rawValue the raw value
	 */
	public Year(Integer rawValue) {
		super(rawValue);
	}

}
