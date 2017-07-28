/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.login;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class HashPassword.
 */
@StringMaxLength(44)
public class HashPassword extends StringPrimitiveValue<HashPassword> {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new hash password.
	 *
	 * @param rawValue the raw value
	 */
	public HashPassword(String rawValue) {
		super(rawValue);
	}
}
