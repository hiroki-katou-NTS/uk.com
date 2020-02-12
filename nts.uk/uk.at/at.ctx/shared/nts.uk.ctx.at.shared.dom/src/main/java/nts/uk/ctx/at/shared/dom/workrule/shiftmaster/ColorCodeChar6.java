/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workrule.shiftmaster;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class ColorCode.
 */
@StringMaxLength(6)
// カラーコード
public class ColorCodeChar6 extends StringPrimitiveValue<ColorCodeChar6> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new color code.
	 *
	 * @param rawValue the raw value
	 */
	public ColorCodeChar6(String rawValue) {
		super(rawValue);
	}
}
