/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * The Class EmTimeFrameNo.
 */
// 就業時間枠NO
@IntegerRange(min = 1, max = 5)
public class EmTimeFrameNo extends IntegerPrimitiveValue<EmTimeFrameNo> {
	private static final long serialVersionUID = 1L;

	public EmTimeFrameNo(Integer rawValue) {
		super(rawValue);
	}

}
