/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * The Class WageTableRefNo.
 */
@IntegerRange(min = 1, max = 3)
public class DemensionOrder extends IntegerPrimitiveValue<DemensionOrder> {

	/**
	 * Instantiates a new wage table ref no.
	 *
	 * @param rawValue
	 *            the raw value
	 */
	public DemensionOrder(Integer rawValue) {
		super(rawValue);
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

}