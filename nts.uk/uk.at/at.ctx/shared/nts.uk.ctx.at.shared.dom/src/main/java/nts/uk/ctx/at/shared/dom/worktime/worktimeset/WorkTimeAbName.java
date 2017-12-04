/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.worktimeset;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class WorkTimeAbName.
 */
//就業時間帯略名
@StringMaxLength(6)
public class WorkTimeAbName extends StringPrimitiveValue<WorkTimeAbName> {
	private static final long serialVersionUID = 1390924144844546717L;

	public WorkTimeAbName(String rawValue) {
		super(rawValue);
	}

}
