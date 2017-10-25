/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.optitem.calculation;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.error.BusinessException;
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
	private FormulaSettingItem leftItem;

	// 計算式設定項目
	/** The right item. */
	private FormulaSettingItem rightItem;

	/**
	 * Instantiates a new formula setting.
	 *
	 * @param memento the memento
	 */
	public FormulaSetting(FormulaSettingGetMemento memento) {
		this.minusSegment = memento.getMinusSegment();
		this.operator = memento.getOperatorAtr();
		this.leftItem = memento.getLeftItem();
		this.rightItem = memento.getRightItem();

		// Validate.
		if (this.isBothItemInput()) {
			throw new BusinessException("Msg_420");
		}
		if (this.rightItem.getSettingMethod() == SettingMethod.NUMERICAL_INPUT && this.isDivideByZero()) {
			throw new BusinessException("Msg_638");
		}
	}

	/**
	 * Checks if is both item input.
	 *
	 * @return true, if is both item input
	 */
	private boolean isBothItemInput() {
		if (this.leftItem.getSettingMethod() == SettingMethod.NUMERICAL_INPUT
				&& this.rightItem.getSettingMethod() == SettingMethod.NUMERICAL_INPUT) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if is divide by zero.
	 *
	 * @return true, if is divide by zero
	 */
	private boolean isDivideByZero() {
		if (this.operator == OperatorAtr.DIVIDE && this.rightItem.getInputValue().v() == BigDecimal.ZERO) {
			return true;
		}
		return false;
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(FormulaSettingSetMemento memento) {
		memento.setMinusSegment(this.minusSegment);
		memento.setOperatorAtr(this.operator);
		memento.setLeftItem(this.leftItem);
		memento.setRightItem(this.rightItem);
	}

}
