/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.basicworkregister;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;

/**
 * The Class WorkplaceId. 職場ID
 */
@StringMaxLength(5)
public class WorkplaceId extends CodePrimitiveValue<WorkplaceId> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new work place id.
	 *
	 * @param rawValue the raw value
	 */
	public WorkplaceId(String rawValue) {
		super(rawValue);
	}

}
