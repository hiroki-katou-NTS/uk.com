/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.optionalitem.calculationformula;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class FormulaSetting.
 */
// 計算式設定
@Getter
public class FormulaSetting extends DomainObject {

	/** The minus segment. */
	// マイナス区分
	private MinusSegment minusSegment;

	/** The operator. */
	// 演算子
	private OperatorAtr operator;

	/** The formula setting items. */
	// 計算式設定項目
	private List<FormulaSettingItem> formulaSettingItems;

}
