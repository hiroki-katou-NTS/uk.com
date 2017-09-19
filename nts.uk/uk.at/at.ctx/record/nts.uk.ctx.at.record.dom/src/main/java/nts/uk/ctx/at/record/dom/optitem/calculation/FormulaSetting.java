/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.optitem.calculation;

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

	/**
	 * Instantiates a new formula setting.
	 *
	 * @param memento the memento
	 */
	public FormulaSetting(FormulaSettingGetMemento memento) {
		this.minusSegment = memento.getMinusSegment();
		this.operator = memento.getOperatorAtr();
		this.formulaSettingItems = memento.getFormulaSettingItems();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(FormulaSettingSetMemento memento) {
		memento.setMinusSegment(this.minusSegment);
		memento.setOperatorAtr(this.operator);
		memento.setFormulaSettingItems(this.formulaSettingItems);
	}

}
