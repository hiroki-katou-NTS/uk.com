/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.worktimeset;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class WorkTimeSymbol.
 */
// 就業時間帯記号名
@StringMaxLength(3)
public class WorkTimeSymbol extends StringPrimitiveValue<WorkTimeSymbol> {

	private static final long serialVersionUID = 4133286692955063587L;

	public WorkTimeSymbol(String rawValue) {
		super(rawValue);
	}

}
