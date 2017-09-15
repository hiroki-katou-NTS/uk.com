/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.optitem;

import lombok.AllArgsConstructor;

/**
 * The Enum OptionalItemAttribute.
 */
// 任意項目の属性
@AllArgsConstructor
public enum OptionalItemAtr {

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
