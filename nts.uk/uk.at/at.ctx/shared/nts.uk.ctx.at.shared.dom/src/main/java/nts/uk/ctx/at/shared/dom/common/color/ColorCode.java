/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.common.color;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class ColorCode.
 */
@StringMaxLength(7)
// カラーコード
public class ColorCode extends StringPrimitiveValue<ColorCode> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new color code.
	 *
	 * @param rawValue the raw value
	 */
	public ColorCode(String rawValue) {
		super(rawValue);
	}
}
