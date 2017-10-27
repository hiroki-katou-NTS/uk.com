/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.basicworkregister;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;

/**
 * The Class WorkingCode. 就業時間帯コード
 */
@StringMaxLength(3)
public class WorkingCode extends CodePrimitiveValue<WorkingCode> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new sift code.
	 *
	 * @param rawValue the raw value
	 */
	public WorkingCode(String rawValue) {
		super(rawValue);
	}
}
