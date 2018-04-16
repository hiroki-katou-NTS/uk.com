/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.calculation.holiday.roundingmonth;

import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class TimeItemId.
 */

@StringMaxLength(36)
public class TimeItemId extends StringPrimitiveValue<PrimitiveValue<String>> {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public TimeItemId(String rawValue) {
		super(rawValue);
	}
}