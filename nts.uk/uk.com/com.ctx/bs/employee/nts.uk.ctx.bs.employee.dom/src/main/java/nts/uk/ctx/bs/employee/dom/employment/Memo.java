/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.employment;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class Memo.
 */
@StringMaxLength(500)
public class Memo extends StringPrimitiveValue<Memo> {

	/**
	 * Instantiates a new memo.
	 *
	 * @param rawValue the raw value
	 */
	public Memo(String rawValue) {
		super(rawValue);
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

}
