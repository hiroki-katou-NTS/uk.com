/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.disporder;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * The Class DispOrder.
 */
@IntegerRange(min = -999, max = 999)
public class DispOrder extends IntegerPrimitiveValue<DispOrder> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new disp order.
	 *
	 * @param rawValue the raw value
	 */
	public DispOrder(Integer rawValue) {
		super(rawValue);
	}

}
