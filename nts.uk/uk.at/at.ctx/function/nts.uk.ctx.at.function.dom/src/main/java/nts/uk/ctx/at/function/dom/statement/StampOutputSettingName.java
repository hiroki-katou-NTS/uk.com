/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.dom.statement;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class StampOutputSettingName.
 */
@StringMaxLength(20)
// 打刻一覧出力設定名称
public class StampOutputSettingName extends StringPrimitiveValue<StampOutputSettingName>{

	/**
	 * Instantiates a new stamp output setting name.
	 *
	 * @param rawValue the raw value
	 */
	public StampOutputSettingName(String rawValue) {
		super(rawValue);
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5457142033392850065L;

}
