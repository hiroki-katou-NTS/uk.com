/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.jobtitle;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;

/**
 * The Class PositionId.
 */
@StringCharType(CharType.NUMERIC)
@StringMaxLength(5)
public class PositionId extends CodePrimitiveValue<PositionId> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new position id.
	 *
	 * @param rawValue the raw value
	 */
	public PositionId(String rawValue) {
		super(rawValue);
	}

}
