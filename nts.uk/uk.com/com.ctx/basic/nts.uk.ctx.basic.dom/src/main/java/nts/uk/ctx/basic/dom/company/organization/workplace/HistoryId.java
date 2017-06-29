/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.workplace;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class HistoryId.
 */
@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(36)
public class HistoryId extends StringPrimitiveValue<HistoryId> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new history id.
	 *
	 * @param rawValue the raw value
	 */
	public HistoryId(String rawValue) {
		super(rawValue);
	}

}