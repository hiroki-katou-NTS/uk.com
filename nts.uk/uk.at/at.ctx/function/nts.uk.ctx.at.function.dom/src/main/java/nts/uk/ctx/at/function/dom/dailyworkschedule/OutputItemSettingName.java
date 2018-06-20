/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.dom.dailyworkschedule;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class OutputitemSettingName.
 * @author HoangDD
 */
@StringMaxLength(20)
// 出力項目設定名称
public class OutputItemSettingName extends StringPrimitiveValue<OutputItemSettingName> {

	/**
	 * Instantiates a new outputitem setting name.
	 *
	 * @param rawValue the raw value
	 */
	public OutputItemSettingName(String rawValue) {
		super(rawValue);
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -423351051216191404L;


}
