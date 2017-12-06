/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * The Class BreakoutFrameNo.
 */
// 休出枠NO
@IntegerRange(min = 1, max = 10)
public class BreakoutFrameNo extends IntegerPrimitiveValue<BreakoutFrameNo> {
	private static final long serialVersionUID = 1L;

	public BreakoutFrameNo(Integer rawValue) {
		super(rawValue);
	}
}
