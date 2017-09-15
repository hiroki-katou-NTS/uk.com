/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.optitem.calculation;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class FormulaSettingItem.
 */
// 計算式設定
@Getter
public class FormulaSettingItem extends AggregateRoot {

	/** The setting method. */
	// 設定方法
	private SettingMethod settingMethod;

	/** The disp order. */
	// 順番
	private int dispOrder;

	// ===================== Optional ======================= //
	/** The input value. */
	// 入力値
	private InputValue inputValue;

	/** The formula item id. */
	// 計算式項目ID
	private FormulaId formulaItemId;

}
