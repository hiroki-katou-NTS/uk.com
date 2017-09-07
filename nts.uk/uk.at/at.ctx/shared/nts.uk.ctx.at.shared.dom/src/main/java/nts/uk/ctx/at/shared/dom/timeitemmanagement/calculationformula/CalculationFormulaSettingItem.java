/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.timeitemmanagement.calculationformula;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class CalculationFormulaSettingItem.
 */
// 計算式設定
@Getter
public class CalculationFormulaSettingItem extends AggregateRoot {

	/** The setting method. */
	// 設定方法
	private SettingMethod settingMethod;

	/** The disp order. */
	// 順番
	private int dispOrder;

	/** The input value. */
	// 入力値
	private OptionalItemInputValue inputValue;

	/** The calculation formula item id. */
	// 計算式項目ID
	private String calculationFormulaItemId;

}
