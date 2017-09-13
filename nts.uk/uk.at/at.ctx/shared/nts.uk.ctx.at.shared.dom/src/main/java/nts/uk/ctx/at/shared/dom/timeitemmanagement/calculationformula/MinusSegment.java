/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.timeitemmanagement.calculationformula;

import lombok.AllArgsConstructor;

/**
 * The Enum CalculationClassification.
 */
// 設定方法
@AllArgsConstructor
public enum MinusSegment {

	// 0として扱わない
	NOT_TREATED_AS_ZERO(0),

	// 0として扱う
	TREATED_AS_ZERO(1);

	/** The value. */
	public final int value;
}
