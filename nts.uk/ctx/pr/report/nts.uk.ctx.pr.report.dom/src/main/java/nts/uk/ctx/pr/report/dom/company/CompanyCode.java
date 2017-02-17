/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.company;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;

/**
 * The Class CompanyCode.
 */
@StringCharType(CharType.NUMERIC)
@StringMaxLength(4)
public class CompanyCode extends CodePrimitiveValue<CompanyCode> {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs.
	 * 
	 * @param rawValue raw value
	 */
	public CompanyCode(String rawValue) {
		super(rawValue);
	}

}
