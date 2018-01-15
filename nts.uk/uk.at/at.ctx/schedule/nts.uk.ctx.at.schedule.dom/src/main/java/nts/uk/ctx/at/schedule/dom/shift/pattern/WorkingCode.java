/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.pattern;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class WorkingCode.
 */
// 就業時間帯コード
@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(3)
public class WorkingCode extends StringPrimitiveValue<WorkingCode>{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4042037564670742671L;

	/**
	 * Instantiates a new working code.
	 *
	 * @param rawValue the raw value
	 */
	public WorkingCode(String rawValue) {
		super(rawValue);
	}

}

