/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.common.calendar;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * The Class MonthlyNew.
 */
// 月度
@IntegerRange(max = 12, min = 1)
public class Month extends IntegerPrimitiveValue<Month> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4727391399987061483L;

	/**
	 * Instantiates a new monthly new.
	 *
	 * @param rawValue
	 *            the raw value
	 */
	public Month(Integer rawValue) {
		super(rawValue);
	}
}
