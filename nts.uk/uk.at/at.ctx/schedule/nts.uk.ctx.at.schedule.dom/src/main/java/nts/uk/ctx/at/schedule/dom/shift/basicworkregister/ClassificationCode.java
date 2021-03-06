/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.basicworkregister;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;

/**
 * The Class ClassificationCode.
 */
@StringMaxLength(10)
public class ClassificationCode extends CodePrimitiveValue<ClassificationCode> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new classification code.
	 *
	 * @param rawValue the raw value
	 */
	public ClassificationCode(String rawValue) {
		super(rawValue);
	}

}
