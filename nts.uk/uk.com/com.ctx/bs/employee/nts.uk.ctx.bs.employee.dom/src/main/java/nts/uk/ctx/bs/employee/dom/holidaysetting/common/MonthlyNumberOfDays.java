/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.holidaysetting.common;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMaxValue;
import nts.arc.primitive.constraint.DecimalMinValue;

/**
 * The Class MonthlyNumberOfDays.
 */
@DecimalMinValue("0.0")
@DecimalMaxValue("31.0")
// 月間日数
public class MonthlyNumberOfDays extends DecimalPrimitiveValue<MonthlyNumberOfDays> {
	/**
	 * Instantiates a new monthly number of days.
	 *
	 * @param rawValue the raw value
	 */
	public MonthlyNumberOfDays(BigDecimal rawValue) {
		super(rawValue);
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
}