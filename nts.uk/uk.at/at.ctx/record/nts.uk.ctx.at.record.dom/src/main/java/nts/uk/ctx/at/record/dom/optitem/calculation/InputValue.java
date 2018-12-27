/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.optitem.calculation;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMantissaMaxLength;
import nts.arc.primitive.constraint.DecimalRange;

/**
 * The Class InputValue.
 */
// 任意項目入力値
@Getter
@DecimalMantissaMaxLength(2)
@DecimalRange(min = "0.00", max = "99999.99")
public class InputValue extends DecimalPrimitiveValue<InputValue> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new input value.
	 *
	 * @param rawValue the raw value
	 */
	public InputValue(BigDecimal rawValue) {
		super(rawValue);
	}

}