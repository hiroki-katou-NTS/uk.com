/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.payment.refundsetting.refundpadding;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalRange;

/**
 * The Class RangeLimit.
 */
@DecimalRange(min = "-999.99", max = "999.99")
public class SizeLimit extends DecimalPrimitiveValue<SizeLimit> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new range limit.
	 *
	 * @param rawValue
	 *            the raw value
	 */
	public SizeLimit(BigDecimal rawValue) {
		super(rawValue);
	}

}
