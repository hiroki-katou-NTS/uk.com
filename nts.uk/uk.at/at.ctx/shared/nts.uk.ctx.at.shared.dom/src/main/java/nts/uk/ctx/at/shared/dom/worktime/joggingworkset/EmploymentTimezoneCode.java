/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.joggingworkset;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
 * The Class EmploymentTimezoneCode.
 */
@StringMaxLength(3)
@StringCharType(CharType.ALPHA_NUMERIC)
@ZeroPaddedCode
public class EmploymentTimezoneCode extends CodePrimitiveValue<EmploymentTimezoneCode> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new employment timezone code.
	 *
	 * @param rawValue the raw value
	 */
	public EmploymentTimezoneCode(String rawValue) {
		super(rawValue);
	}

}
