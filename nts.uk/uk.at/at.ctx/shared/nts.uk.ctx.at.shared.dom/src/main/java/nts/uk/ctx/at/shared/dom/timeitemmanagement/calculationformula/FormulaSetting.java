/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.timeitemmanagement.calculationformula;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class DispOrder.
 */
// 計算式設定
@Getter
public class FormulaSetting extends AggregateRoot {

	/** The company id. */
	// マイナス区分
	private MinusSegment minusSegment;

	/** The optional item no. */
	// 演算子
	private CalculationClassification operator;

	/** The optional item formula id. */
	// 計算式設定項目
	private CalculationFormulaSettingItem calculationFormulaSettingItem;

}
