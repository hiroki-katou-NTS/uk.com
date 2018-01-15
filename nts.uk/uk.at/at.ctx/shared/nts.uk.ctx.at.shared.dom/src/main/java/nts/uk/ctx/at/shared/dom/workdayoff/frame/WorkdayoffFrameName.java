/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workdayoff.frame;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class WorkdayoffFrameName.
 */
//休出枠名称
@StringMaxLength(12)
public class WorkdayoffFrameName extends StringPrimitiveValue<WorkdayoffFrameName> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3072948248326997648L;

	/**
	 * Instantiates a new workdayoff frame name.
	 *
	 * @param rawValue the raw value
	 */
	public WorkdayoffFrameName(String rawValue) {
		super(rawValue);
	}
	
}
