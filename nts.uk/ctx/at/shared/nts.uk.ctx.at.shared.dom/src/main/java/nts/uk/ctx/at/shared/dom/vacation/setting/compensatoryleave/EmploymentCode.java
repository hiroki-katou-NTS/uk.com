/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;

/**
 * The Class EmploymentCode.
 */
@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(2)
public class EmploymentCode extends CodePrimitiveValue<EmploymentCode>{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public EmploymentCode(String rawValue) {
		super(rawValue);
	}

}
