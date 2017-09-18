/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.department;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;

/**
 * The Class DepartmentCode. 部門コード
 */
@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(10)
public class DepartmentCode extends CodePrimitiveValue<DepartmentCode>{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new department code.
	 *
	 * @param rawValue the raw value
	 */
	public DepartmentCode(String rawValue) {
		super(rawValue);
	}

}
