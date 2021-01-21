/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * The Class WorkNo.
 */
// 勤務NO
@IntegerRange(min = 1, max = 3)
public class WorkNo extends IntegerPrimitiveValue<WorkNo> {

	private static final long serialVersionUID = 1L;

	public WorkNo(Integer rawValue) {
		super(rawValue);
	}

}
