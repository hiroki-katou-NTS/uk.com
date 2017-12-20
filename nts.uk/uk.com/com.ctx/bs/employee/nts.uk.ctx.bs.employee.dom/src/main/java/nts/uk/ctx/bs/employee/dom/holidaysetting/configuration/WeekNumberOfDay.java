/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.holidaysetting.configuration;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerRange;

/**
 * The Class WeekNumberOfDay.
 */
@HalfIntegerRange(min = 0, max = 7)
// 週間日数
public class WeekNumberOfDay extends HalfIntegerPrimitiveValue<WeekNumberOfDay>{

	/**
	 * Instantiates a new week number of day.
	 *
	 * @param rawValue the raw value
	 */
	public WeekNumberOfDay(Double rawValue) {
		super(rawValue);
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 168902067765454308L;

}
