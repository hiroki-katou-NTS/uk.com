/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.common.numberrounding;

import lombok.AllArgsConstructor;

/**
 * The Enum Rounding.
 */
// 端数処理
@AllArgsConstructor
public enum Rounding {

	/** The truncation. */
	// 切り捨て
	TRUNCATION(0),

	/** The round up. */
	// 切り上げ
	ROUND_UP(1),

	/** The down 4 up 5. */
	// 四捨五入
	DOWN_4_UP_5(1);

	/** The value. */
	public final int value;
}
