/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.pattern;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;

/**
 * The Class WorkTypeCode.
 */
// 勤務種類設定コード
@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(3)
public class WorkTypeCode extends CodePrimitiveValue<WorkTypeCode>{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new work type code.
	 *
	 * @param rawValue the raw value
	 */
	public WorkTypeCode(String rawValue) {
		super(rawValue);
	}
	

}
