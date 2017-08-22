/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.person;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class PersonName.
 */
// 個人名
@StringMaxLength(120)
public class PersonName extends StringPrimitiveValue<PersonName> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new employee name.
	 *
	 * @param rawValue the raw value
	 */
	public PersonName(String rawValue) {
		super(rawValue);
	}

}
