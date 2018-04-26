/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.ot.frame;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMantissaMaxLength;
import nts.arc.primitive.constraint.DecimalRange;

/**
 * The Class OvertimeWorkFrameNo.
 */
//残業枠NO
@DecimalRange(min = "1", max = "10")
@DecimalMantissaMaxLength(2)
public class OvertimeWorkFrameNo extends DecimalPrimitiveValue<OvertimeWorkFrameNo> {
	
	/**
	 * Instantiates a new overtime work frame no.
	 *
	 * @param rawValue the raw value
	 */
	public OvertimeWorkFrameNo(BigDecimal rawValue) {
		super(rawValue);
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Gets the default data.
	 *
	 * @return the default data
	 */
	public static OvertimeWorkFrameNo getDefaultData() {
		return new OvertimeWorkFrameNo(BigDecimal.ONE);
	}
}
