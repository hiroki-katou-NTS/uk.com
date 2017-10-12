/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.jobtitle;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class PositionId.
 */
@StringMaxLength(36)
public class JobTitleId extends StringPrimitiveValue<JobTitleId> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new position id.
	 *
	 * @param rawValue the raw value
	 */
	public JobTitleId(String rawValue) {
		super(rawValue);
	}

}
