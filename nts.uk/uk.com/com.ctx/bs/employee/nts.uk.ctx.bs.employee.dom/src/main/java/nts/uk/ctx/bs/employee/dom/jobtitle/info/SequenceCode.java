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
 * The Class SequenceCode.
 */
@StringCharType(CharType.NUMERIC)
@StringMaxLength(2)
@ZeroPaddedCode
public class SequenceCode extends CodePrimitiveValue<SequenceCode> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new sequence code.
	 *
	 * @param rawValue the raw value
	 */
	public SequenceCode(String rawValue) {
		super(rawValue);
	}

}
