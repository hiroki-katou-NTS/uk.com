/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class BreakdownItemName.
 */
// 内訳項目名称
@StringMaxLength(30)
public class BreakdownItemName extends StringPrimitiveValue<BreakdownItemName>{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new breakdown item name.
	 *
	 * @param rawValue the raw value
	 */
	public BreakdownItemName(String rawValue) {
		super(rawValue);
	}

}
