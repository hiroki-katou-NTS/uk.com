/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMantissaMaxLength;
import nts.arc.primitive.constraint.DecimalRange;

/**
 * The Class WorkNo.
 */
// 勤務NO
@DecimalRange(min = "1", max = "3")
@DecimalMantissaMaxLength(1)
public class WorkNo extends IntegerPrimitiveValue<WorkNo> {

	private static final long serialVersionUID = 1L;

	public WorkNo(Integer rawValue) {
		super(rawValue);
	}

}
