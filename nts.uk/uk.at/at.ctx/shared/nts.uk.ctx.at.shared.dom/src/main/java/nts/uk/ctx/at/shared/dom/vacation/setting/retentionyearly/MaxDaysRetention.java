/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

/**
 * The Class MaxDaysRetention. 積立年休上限日数
 */
@IntegerMaxValue(999)
@IntegerMinValue(0)
public class MaxDaysRetention extends IntegerPrimitiveValue<MaxDaysRetention> {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new max days off cumulation.
	 *
	 * @param rawValue the raw value
	 */
	public MaxDaysRetention(Integer rawValue) {
		super(rawValue);
	}

}
