/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workrule.closure;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * The Class ClosureDay.
 */
@IntegerRange(max = 30, min = 0)
public class ClosureDay extends IntegerPrimitiveValue<ClosureDay> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4727391399987061483L;

	/**
	 * Instantiates a new closure day.
	 *
	 * @param rawValue
	 *            the raw value
	 */
	public ClosureDay(Integer rawValue) {
		super(rawValue);
	}

}
