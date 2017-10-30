/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.personallaborcondition;

import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class WorkTimeCode.
 */
// 就業時間帯コード
@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(3)
public class WorkTimeCode extends StringPrimitiveValue<PrimitiveValue<String>>{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new work time code.
	 *
	 * @param rawValue the raw value
	 */
	public WorkTimeCode(String rawValue) {
		super(rawValue);
	}

}
