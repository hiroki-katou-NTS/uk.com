/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;

/**
 * The Class PositionId.
 */
@StringMaxLength(36)
public class JobTitleId extends CodePrimitiveValue<JobTitleId> {

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
