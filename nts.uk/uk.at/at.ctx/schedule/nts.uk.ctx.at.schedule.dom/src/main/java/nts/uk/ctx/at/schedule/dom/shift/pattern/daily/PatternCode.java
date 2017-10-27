/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.pattern.daily;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class Priority.
 */
@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(2)
public class PatternCode extends StringPrimitiveValue<PatternCode> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2960364556648891076L;

	/**
	 * Instantiates a new priority.
	 *
	 * @param rawValue
	 *            the raw value
	 */
	public PatternCode(String rawValue) {
		super(rawValue);
	}

}
