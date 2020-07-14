/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import java.io.Serializable;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * The Class WorkNo.
 */
// 勤務NO
@IntegerRange(min = 1, max = 3)
public class WorkNo extends IntegerPrimitiveValue<WorkNo> implements Serializable {

	private static final long serialVersionUID = 1L;

	public WorkNo(Integer rawValue) {
		super(rawValue);
	}

}
