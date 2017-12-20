/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.worktimeset;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

//就業時間帯名称
/**
 * The Class WorkTimeName.
 */
@StringMaxLength(12)
public class WorkTimeName extends StringPrimitiveValue<WorkTimeName> {
	private static final long serialVersionUID = -2553705539399959368L;

	public WorkTimeName(String rawValue) {
		super(rawValue);
	}

}
