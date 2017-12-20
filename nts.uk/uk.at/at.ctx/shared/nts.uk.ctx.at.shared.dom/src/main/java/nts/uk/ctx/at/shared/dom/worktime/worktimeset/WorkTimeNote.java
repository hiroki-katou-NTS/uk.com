/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.worktimeset;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class WorkTimeNote.
 */
//就業時間帯備考
@StringMaxLength(40)
public class WorkTimeNote extends StringPrimitiveValue<WorkTimeNote>{
	private static final long serialVersionUID = 8667178942183878371L;

	public WorkTimeNote(String rawValue) {
		super(rawValue);
	}

}
