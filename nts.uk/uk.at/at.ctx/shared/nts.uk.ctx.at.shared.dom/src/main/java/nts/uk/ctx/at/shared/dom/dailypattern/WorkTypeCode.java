/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.dailypattern;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class Priority.
 */
@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(3)
public class WorkTypeCode extends StringPrimitiveValue<WorkTypeCode> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2960364556648891076L;

	/**
	 * Instantiates a new priority.
	 *
	 * @param rawValue
	 *            the raw value
	 */
	public WorkTypeCode(String rawValue) {
		super(rawValue);
	}

}
