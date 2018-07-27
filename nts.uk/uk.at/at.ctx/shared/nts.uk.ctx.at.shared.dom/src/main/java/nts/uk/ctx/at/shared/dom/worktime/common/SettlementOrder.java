/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * The Class SettlementOrder.
 */
//精算順序
@IntegerRange(min = 1, max = 10)
public class SettlementOrder extends IntegerPrimitiveValue<SettlementOrder> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new settlement order.
	 *
	 * @param rawValue the raw value
	 */
	public SettlementOrder(Integer rawValue) {
		super(rawValue);
	}

	/**
	 * Gets the default data.
	 *
	 * @return the default data
	 */
	public static SettlementOrder getDefaultData() {
		return new SettlementOrder(1);
	}

}
