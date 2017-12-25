/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMantissaMaxLength;
import nts.arc.primitive.constraint.DecimalRange;

/**
 * The Class OTFrameNo.
 */
// 残業枠NO
@DecimalRange(min = "1", max = "10")
@DecimalMantissaMaxLength(2)
public class OTFrameNo extends IntegerPrimitiveValue<OTFrameNo> {

	private static final long serialVersionUID = 1L;

	public OTFrameNo(Integer rawValue) {
		super(rawValue);
	}

}
