/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workrecord;

import lombok.AllArgsConstructor;

/**
 * The Enum CalculationRangeCheck.
 */
// 計算範囲チェック
@AllArgsConstructor
public enum CalculationRangeCheck {

	/** The not set. */
	// 設定しない
	NOT_SET(0),

	/** The set. */
	// 設定する
	SET(1);

	/** The value. */
	public final int value;
}
