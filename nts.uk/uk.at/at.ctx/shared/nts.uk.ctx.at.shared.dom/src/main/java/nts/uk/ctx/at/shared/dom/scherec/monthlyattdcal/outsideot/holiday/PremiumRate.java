/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.holiday;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * The Class PremiumRate.
 */
// 割増率
@IntegerRange(min = 0, max = 100)
public class PremiumRate extends IntegerPrimitiveValue<PremiumRate>{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new premium rate.
	 *
	 * @param rawValue the raw value
	 */
	public PremiumRate(Integer rawValue) {
		super(rawValue);
	}

}
