/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * The Class EmTimezoneNo.
 */
// 就業時間帯NO
@IntegerRange(min = 1, max = 10)
public class EmTimezoneNo extends IntegerPrimitiveValue<EmTimezoneNo> {
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new em timezone no.
	 *
	 * @param rawValue the raw value
	 */
	public EmTimezoneNo(Integer rawValue) {
		super(rawValue);
	}

}
