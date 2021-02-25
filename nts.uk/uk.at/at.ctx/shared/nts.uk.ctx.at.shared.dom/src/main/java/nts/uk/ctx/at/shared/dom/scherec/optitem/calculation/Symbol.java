/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.optitem.calculation;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class Symbol.
 */
@StringMaxLength(2)
// 記号
public class Symbol extends StringPrimitiveValue<Symbol> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new symbol.
	 *
	 * @param rawValue the raw value
	 */
	public Symbol(String rawValue) {
		super(rawValue);
	}
}
