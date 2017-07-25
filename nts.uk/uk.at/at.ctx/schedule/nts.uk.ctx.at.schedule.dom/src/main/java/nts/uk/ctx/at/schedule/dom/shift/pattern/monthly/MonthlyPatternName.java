/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.pattern.monthly;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class MonthlyPatternName.
 */
//月間パターンコード
@StringMaxLength(20)
public class MonthlyPatternName extends StringPrimitiveValue<MonthlyPatternName>{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new monthly pattern name.
	 *
	 * @param rawValue the raw value
	 */
	public MonthlyPatternName(String rawValue) {
		super(rawValue);
	}
}
