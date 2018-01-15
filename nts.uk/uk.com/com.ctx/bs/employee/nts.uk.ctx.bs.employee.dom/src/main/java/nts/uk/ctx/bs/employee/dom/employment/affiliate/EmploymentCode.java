/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.employment.affiliate;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;

/**
 * The Class EmploymentCode.
 */
@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(2)
public class EmploymentCode extends CodePrimitiveValue<EmploymentCode> {

	/**
	 * Instantiates a new employment code.
	 *
	 * @param rawValue the raw value
	 */
	public EmploymentCode(String rawValue) {
		super(rawValue);
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

}
