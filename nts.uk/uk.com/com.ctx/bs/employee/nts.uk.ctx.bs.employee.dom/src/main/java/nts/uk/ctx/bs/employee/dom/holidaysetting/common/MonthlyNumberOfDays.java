/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.holidaysetting.common;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerMaxValue;
import nts.arc.primitive.constraint.HalfIntegerMinValue;

/**
 * The Class MonthlyNumberOfDays.
 */
@HalfIntegerMinValue(0.0)
@HalfIntegerMaxValue(31.0)
// 月間日数
public class MonthlyNumberOfDays extends HalfIntegerPrimitiveValue<MonthlyNumberOfDays> {
	
	/**
	 * Instantiates a new monthly number of days.
	 *
	 * @param rawValue the raw value
	 */
	public MonthlyNumberOfDays(Double rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L; 
}