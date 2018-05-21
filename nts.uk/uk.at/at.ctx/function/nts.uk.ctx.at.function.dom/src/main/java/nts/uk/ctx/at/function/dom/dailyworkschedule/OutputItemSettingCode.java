/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.dom.dailyworkschedule;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
 * The Class OutputItemSettingCode.
 * @author HoangDD
 */
@IntegerMaxValue(99)
// 出力項目設定コード
public class OutputItemSettingCode extends IntegerPrimitiveValue<OutputItemSettingCode>{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 957215815666122627L;

	/**
	 * Instantiates a new output item setting code.
	 *
	 * @param rawValue the raw value
	 */
	public OutputItemSettingCode(Integer rawValue) {
		super(rawValue);
	}


}
