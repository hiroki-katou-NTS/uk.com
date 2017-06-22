/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.workplace;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;

/**
 * 
 *階層コード
 */
@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(30)
public class HierarchyCode extends CodePrimitiveValue<HierarchyCode>{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public HierarchyCode(String rawValue) {
		super(rawValue);
	}

}
