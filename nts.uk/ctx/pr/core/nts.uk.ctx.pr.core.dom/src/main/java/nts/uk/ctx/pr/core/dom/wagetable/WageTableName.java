/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class WageTableName.
 */
@StringMaxLength(30)
public class WageTableName extends StringPrimitiveValue<WageTableName> {

	/**
	 * Instantiates a new wage table name.
	 *
	 * @param rawValue the raw value
	 */
	public WageTableName(String rawValue) {
		super(rawValue);
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

}
