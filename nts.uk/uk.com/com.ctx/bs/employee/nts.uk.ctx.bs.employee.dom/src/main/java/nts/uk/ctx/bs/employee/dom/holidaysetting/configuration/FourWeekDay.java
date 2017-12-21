/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.holidaysetting.configuration;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerRange;

/**
 * The Class FourWeekDay.
 */
// 4週日数
@HalfIntegerRange(min = 0, max = 28)
public class FourWeekDay extends HalfIntegerPrimitiveValue<FourWeekDay>{

	/**
	 * Instantiates a new four week day.
	 *
	 * @param rawValue the raw value
	 */
	public FourWeekDay(Double rawValue) {
		super(rawValue);
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8532136529772735368L;

}
