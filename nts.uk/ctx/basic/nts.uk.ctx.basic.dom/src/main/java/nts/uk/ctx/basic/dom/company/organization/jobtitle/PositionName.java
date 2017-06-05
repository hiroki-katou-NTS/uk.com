/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.jobtitle;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class PositionName.
 */
@StringMaxLength(20)
public class PositionName extends StringPrimitiveValue<PositionName> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new position name.
	 *
	 * @param rawValue the raw value
	 */
	public PositionName(String rawValue) {
		super(rawValue);
	}

}
