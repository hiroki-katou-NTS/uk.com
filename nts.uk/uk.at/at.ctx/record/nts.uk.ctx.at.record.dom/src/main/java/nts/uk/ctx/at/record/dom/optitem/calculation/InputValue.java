/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.optitem.calculation;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.primitive.DecimalPrimitiveValue;

/**
 * The Class OptionalItemInputValue.
 */
// 任意項目入力値
@Getter
public class InputValue extends DecimalPrimitiveValue<InputValue> {
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new optional item input value.
	 *
	 * @param rawValue the raw value
	 */
	public InputValue(BigDecimal rawValue) {
		super(rawValue);
	}

}