/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workrecord.calculationformula;

import lombok.AllArgsConstructor;

/**
 * The Enum CalculationFormulaAttribute.
 */
// 計算式属性
@AllArgsConstructor
public enum CalculationFormulaAttribute {

	/** The times. */
	// 回数
	TIMES(0),

	/** The amount. */
	// 金額
	AMOUNT(1),

	/** The time. */
	// 時間
	TIME(2);

	/** The value. */
	public final int value;
}
