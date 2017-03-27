/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class Address.
 */
@StringMaxLength(120)
public class Address extends StringPrimitiveValue<Address> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new address.
	 *
	 * @param rawValue the raw value
	 */
	public Address(String rawValue) {
		super(rawValue);
	}

}
