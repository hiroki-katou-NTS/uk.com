/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.schedule.basicschedule.personalfee;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * The Class PersonalCostAmount.
 */
// 人件費金額
@IntegerRange(min = 0, max = 480000)
public class PersonalFeeAmount extends IntegerPrimitiveValue<PersonalFeeAmount> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new personal cost amount.
	 *
	 * @param rawValue the raw value
	 */
	public PersonalFeeAmount(Integer rawValue) {
		super(rawValue);
	}
}
