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
 * The Class MonthlyPatternCode.
 */
//月間パターンコード
@StringCharType(CharType.NUMERIC)
@StringMaxLength(3)
public class MonthlyPatternCode extends CodePrimitiveValue<MonthlyPatternCode>{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new monthly pattern code.
	 *
	 * @param rawValue the raw value
	 */
	public MonthlyPatternCode(String rawValue) {
		super(rawValue);
	}
}
