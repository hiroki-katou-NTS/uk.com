/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.optitem.calculation;

import lombok.AllArgsConstructor;

/**
 * The Enum SettingItemOrder.
 */
// 計算式項目順番
@AllArgsConstructor
public enum SettingItemOrder {

	/** The left. */
	LEFT(1),

	/** The right. */
	RIGHT(2);

	/** The value. */
	public final int value;
}
