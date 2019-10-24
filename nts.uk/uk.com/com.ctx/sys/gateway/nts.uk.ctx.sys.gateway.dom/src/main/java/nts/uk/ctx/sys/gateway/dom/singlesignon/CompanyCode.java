/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.singlesignon;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class CompanyCode.
 * 他システム会社コード
 */
@StringMaxLength(256)
@StringCharType(CharType.ANY_HALF_WIDTH)
public class CompanyCode extends StringPrimitiveValue<CompanyCode> {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new company code.
	 *
	 * @param rawValue the raw value
	 */
	public CompanyCode(String rawValue) {
		super(rawValue);
	}

}
