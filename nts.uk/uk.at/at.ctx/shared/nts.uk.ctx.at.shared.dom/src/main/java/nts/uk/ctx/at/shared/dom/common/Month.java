/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.common;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * The Class Month.
 */
@IntegerRange(min = 1, max = 12)
public class Month extends IntegerPrimitiveValue<Month> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new month.
	 *
	 * @param rawValue the raw value
	 */
	public Month(Integer rawValue) {
		super(rawValue);
	}

}
