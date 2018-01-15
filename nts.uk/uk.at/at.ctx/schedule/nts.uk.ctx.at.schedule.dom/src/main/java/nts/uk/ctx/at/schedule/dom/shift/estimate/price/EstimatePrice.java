/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.price;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * The Class EstimatePrice.
 */
// 目安金額
@IntegerRange(min = 0, max = 99999999)
public class EstimatePrice extends IntegerPrimitiveValue<EstimatePrice> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new estimate price.
	 *
	 * @param rawValue the raw value
	 */
	public EstimatePrice(Integer rawValue) {
		super(rawValue);
	}

}
