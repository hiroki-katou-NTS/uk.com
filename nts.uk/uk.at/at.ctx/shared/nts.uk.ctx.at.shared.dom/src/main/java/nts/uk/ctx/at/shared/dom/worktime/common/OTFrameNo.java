/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMantissaMaxLength;
import nts.arc.primitive.constraint.DecimalRange;

/**
 * The Class OTFrameNo.
 */
// 残業枠NO
@DecimalRange(min = "1", max = "10")
@DecimalMantissaMaxLength(2)
public class OTFrameNo extends DecimalPrimitiveValue<OTFrameNo> {

	private static final long serialVersionUID = 1L;

	public OTFrameNo(BigDecimal rawValue) {
		super(rawValue);
	}

}
