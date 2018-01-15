/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMantissaMaxLength;
import nts.arc.primitive.constraint.DecimalRange;

//休出枠NO
@DecimalRange(min = "1", max = "10")
@DecimalMantissaMaxLength(2)
public class BreakFrameNo extends DecimalPrimitiveValue<BreakFrameNo> {
	
	/**
	 * Instantiates a new workdayoff frame no.
	 *
	 * @param rawValue the raw value
	 */
	public BreakFrameNo(BigDecimal rawValue) {
		super(rawValue);
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
}
