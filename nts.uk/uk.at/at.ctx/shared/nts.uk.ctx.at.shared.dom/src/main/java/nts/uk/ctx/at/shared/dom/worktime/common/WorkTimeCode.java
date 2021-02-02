/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import java.io.Serializable;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

//就業時間帯コード
@StringMaxLength(3)
@StringCharType(CharType.ALPHA_NUMERIC)
@ZeroPaddedCode
public class WorkTimeCode extends CodePrimitiveValue<WorkTimeCode> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new employment timezone code.
	 *
	 * @param rawValue the raw value
	 */
	public WorkTimeCode(String rawValue) {
		super(rawValue);
	}

}
