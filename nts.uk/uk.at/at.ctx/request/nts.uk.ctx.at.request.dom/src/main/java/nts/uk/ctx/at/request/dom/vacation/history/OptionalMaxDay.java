/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.request.dom.vacation.history;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * The Class OptionalMaxDay.
 */
@IntegerRange(min = 0, max = 99)
//計画休暇を取得できる上限日数
public class OptionalMaxDay extends IntegerPrimitiveValue<OptionalMaxDay>{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new optional max day.
	 *
	 * @param rawValue the raw value
	 */
	public OptionalMaxDay(Integer rawValue) {
		super(rawValue);
	}
}
