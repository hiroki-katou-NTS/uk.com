/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.optitem;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class OptionalItemName.
 */
@StringMaxLength(30)
// 任意項目名称
public class OptionalItemName extends StringPrimitiveValue<OptionalItemName> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new optional item name.
	 *
	 * @param rawValue the raw value
	 */
	public OptionalItemName(String rawValue) {
		super(rawValue);
	}

}
