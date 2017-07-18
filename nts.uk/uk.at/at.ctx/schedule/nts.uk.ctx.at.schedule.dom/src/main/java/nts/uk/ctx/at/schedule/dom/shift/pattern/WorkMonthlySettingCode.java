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
 * The Class WorkMonthlySettingCode.
 */
// 勤務種類設定コード
@StringCharType(CharType.NUMERIC)
@StringMaxLength(3)
public class WorkMonthlySettingCode extends CodePrimitiveValue<WorkMonthlySettingCode>{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new work monthly setting code.
	 *
	 * @param rawValue the raw value
	 */
	public WorkMonthlySettingCode(String rawValue) {
		super(rawValue);
	}
	

}
