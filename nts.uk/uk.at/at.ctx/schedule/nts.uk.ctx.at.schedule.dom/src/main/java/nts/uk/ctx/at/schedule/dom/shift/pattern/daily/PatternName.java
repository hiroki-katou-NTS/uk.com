/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.pattern.daily;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class Priority.
 */
@StringMaxLength(12)
public class PatternName extends StringPrimitiveValue<PatternName> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2960364556648891076L;

	/**
	 * Instantiates a new priority.
	 *
	 * @param rawValue
	 *            the raw value
	 */
	public PatternName(String rawValue) {
		super(rawValue);
	}

}
