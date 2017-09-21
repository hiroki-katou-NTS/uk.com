/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.optitem.calculation;

import lombok.AllArgsConstructor;

/**
 * The Enum SettingMethod.
 */
// 設定方法
@AllArgsConstructor
public enum SettingMethod {

	/** The item selection. */
	// 項目選択
	ITEM_SELECTION(0),

	/** The numerical input. */
	// 数値入力
	NUMERICAL_INPUT(1);

	/** The value. */
	public final int value;
}
