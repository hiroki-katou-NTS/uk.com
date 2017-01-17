/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.wageledger.outputsetting;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;

/**
 * The Class OutputSettingCode.
 */
@StringCharType(CharType.NUMERIC)
@StringMaxLength(3)
public class OutputSettingCode extends CodePrimitiveValue<OutputSettingCode> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new output setting code.
	 *
	 * @param rawValue the raw value
	 */
	public OutputSettingCode(String rawValue) {
		super(rawValue);
	}

}
