/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.optitem.calculation;

import lombok.Getter;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class OptionalItemFormulaName.
 */

@Getter
@StringMaxLength(30)
// 任意項目計算式名称
public class FormulaName extends StringPrimitiveValue<FormulaName> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new optional item formula name.
	 *
	 * @param rawValue the raw value
	 */
	public FormulaName(String rawValue) {
		super(rawValue);
	}

}
