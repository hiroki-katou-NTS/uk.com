/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workdayoff.frame;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMantissaMaxLength;
import nts.arc.primitive.constraint.DecimalRange;

/**
 * The Class WorkdayoffFrameNo.
 */
//休出枠NO
@DecimalRange(min = "1", max = "10")
@DecimalMantissaMaxLength(2)
public class WorkdayoffFrameNo extends DecimalPrimitiveValue<WorkdayoffFrameNo> {
	
	/**
	 * Instantiates a new workdayoff frame no.
	 *
	 * @param rawValue the raw value
	 */
	public WorkdayoffFrameNo(BigDecimal rawValue) {
		super(rawValue);
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
}
