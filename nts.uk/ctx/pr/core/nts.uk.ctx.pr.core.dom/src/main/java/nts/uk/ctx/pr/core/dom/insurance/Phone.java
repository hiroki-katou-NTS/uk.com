/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class Phone.
 */
@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(20)
public class Phone extends StringPrimitiveValue<Phone> {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new phone.
	 *
	 * @param rawValue the raw value
	 */
	public Phone(String rawValue) {
		super(rawValue);
	}

}
