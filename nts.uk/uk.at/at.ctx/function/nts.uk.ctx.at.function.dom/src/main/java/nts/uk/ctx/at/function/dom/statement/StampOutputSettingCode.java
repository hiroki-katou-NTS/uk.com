/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.dom.statement;

import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
 * The Class StampOutputSettingCode.
 */
@StringCharType(CharType.NUMERIC)
@StringMaxLength(2)
@ZeroPaddedCode
// 打刻一覧出力設定コード
public class StampOutputSettingCode extends StringPrimitiveValue<PrimitiveValue<String>>{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8810468745270665085L;

	/**
	 * Instantiates a new stamp output setting code.
	 *
	 * @param rawValue the raw value
	 */
	public StampOutputSettingCode(String rawValue) {
		super(rawValue);
	}
}
