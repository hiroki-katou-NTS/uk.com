/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.holidaysetting.common;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * The Class MonthlyNumberOfDays.
 */
@IntegerRange(min = 0, max = 31)
// 月間日数
public class MonthlyNumberOfDays extends IntegerPrimitiveValue<MonthlyNumberOfDays> {
	/**
	 * Instantiates a new monthly number of days.
	 *
	 * @param rawValue the raw value
	 */
	public MonthlyNumberOfDays(Integer rawValue) {
		super(rawValue);
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
}