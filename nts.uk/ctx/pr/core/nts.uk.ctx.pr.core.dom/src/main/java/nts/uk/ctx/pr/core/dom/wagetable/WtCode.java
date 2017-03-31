/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class WageTableCode.
 */
@StringMaxLength(3)
@StringCharType(CharType.NUMERIC)
public class WtCode extends StringPrimitiveValue<WtCode> {

	/**
	 * Instantiates a new wage table code.
	 *
	 * @param rawValue
	 *            the raw value
	 */
	public WtCode(String rawValue) {
		super(rawValue);
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

}
