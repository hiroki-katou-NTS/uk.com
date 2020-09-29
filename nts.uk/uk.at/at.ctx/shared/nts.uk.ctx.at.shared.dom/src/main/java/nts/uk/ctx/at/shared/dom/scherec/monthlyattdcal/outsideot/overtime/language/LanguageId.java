/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.language;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class LanguageId.
 */
@StringMaxLength(3)
// 言語ID
public class LanguageId extends StringPrimitiveValue<LanguageId>{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new language id.
	 *
	 * @param rawValue the raw value
	 */
	public LanguageId(String rawValue) {
		super(rawValue);
	}

}
