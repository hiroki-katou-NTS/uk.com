/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.closure;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class ClosureHistoryId.
 */
@StringMaxLength(36)
public class ClosureHistoryId extends StringPrimitiveValue<ClosureHistoryId> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4042037564670742671L;

	/**
	 * Instantiates a new closure history id.
	 *
	 * @param rawValue the raw value
	 */
	public ClosureHistoryId(String rawValue) {
		super(rawValue);
	}

}
