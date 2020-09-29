/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.optitem.calculation;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class OptionalItemFormulaId.
 */
// 任意項目計算式ID
@StringMaxLength(36)
public class FormulaId extends StringPrimitiveValue<FormulaId> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new optional item formula id.
	 *
	 * @param rawValue the raw value
	 */
	public FormulaId(String rawValue) {
		super(rawValue);
	}
}
