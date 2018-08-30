/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.optitem;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class UnitOfOptionalItem.
 */
@StringMaxLength(2)
// 任意項目の単位
public class UnitOfOptionalItem extends StringPrimitiveValue<UnitOfOptionalItem> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new unit of optional item.
	 *
	 * @param rawValue the raw value
	 */
	public UnitOfOptionalItem(String rawValue) {
		super(rawValue);
	}

}
