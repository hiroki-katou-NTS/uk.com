/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.timeitemmanagement.calculationformula;

import lombok.AllArgsConstructor;

/**
 * The Enum CalculationClassification.
 */
// 計算区分
@AllArgsConstructor
public enum CalculationClassification {

	/** The embedded formula. */
	// 計算式組込
	EMBEDDED_FORMULA(0),

	/** The formula setting. */
	// 計算式設定
	FORMULA_SETTING(1);

	/** The value. */
	public final int value;
}
