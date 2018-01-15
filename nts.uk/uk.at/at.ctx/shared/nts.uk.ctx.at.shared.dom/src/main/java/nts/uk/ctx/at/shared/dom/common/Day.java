/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.common;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * The Class Day.
 */
@IntegerRange(min = 1, max = 31)
public class Day extends IntegerPrimitiveValue<Day> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new day.
	 *
	 * @param rawValue the raw value
	 */
	public Day(Integer rawValue) {
		super(rawValue);
	}

}
