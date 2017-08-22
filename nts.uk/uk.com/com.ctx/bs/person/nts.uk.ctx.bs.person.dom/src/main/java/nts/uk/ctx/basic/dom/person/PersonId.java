/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.person;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class PersonId.
 */
@StringMaxLength(10)
public class PersonId extends StringPrimitiveValue<PersonId> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new person id.
	 *
	 * @param rawValue the raw value
	 */
	public PersonId(String rawValue) {
		super(rawValue);
	}

}
