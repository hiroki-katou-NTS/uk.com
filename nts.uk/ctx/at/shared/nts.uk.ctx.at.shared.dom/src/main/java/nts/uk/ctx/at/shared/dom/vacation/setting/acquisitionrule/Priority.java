/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

/**
 * The Class Priority.
 */
@IntegerMaxValue(6)
@IntegerMinValue(1)
public class Priority extends IntegerPrimitiveValue<Priority>{

	public Priority(Integer rawValue) {
		super(rawValue);
	}

}
