/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.loginold;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class MailAddress.
 */
@StringCharType(CharType.ANY_HALF_WIDTH)
@StringMaxLength(80)
public class MailAddress extends StringPrimitiveValue<MailAddress> {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new mail address.
	 *
	 * @param rawValue the raw value
	 */
	public MailAddress(String rawValue) {
		super(rawValue);
	}
}