/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.timeitemmanagement.calculationformula;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.primitive.DecimalPrimitiveValue;

/**
 * The Class OptionalItemInputValue.
 */
// 任意項目入力値
@Getter
public class OptionalItemInputValue extends DecimalPrimitiveValue<OptionalItemInputValue> {
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new optional item input value.
	 *
	 * @param rawValue the raw value
	 */
	public OptionalItemInputValue(BigDecimal rawValue) {
		super(rawValue);
	}

}