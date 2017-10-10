/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.jobtitle.info;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
 * The Class PositionCode.
 */
@StringCharType(CharType.NUMERIC)
@StringMaxLength(5)
@ZeroPaddedCode
public class JobTitleCode extends CodePrimitiveValue<JobTitleCode> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new position code.
	 *
	 * @param rawValue the raw value
	 */
	public JobTitleCode(String rawValue) {
		super(rawValue);
	}

}
