/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.optitem.calculation;

/**
 * The Interface FormulaSettingSetMemento.
 */
public interface FormulaSettingSetMemento {

	/**
	 * Sets the minus segment.
	 *
	 * @param segment the new minus segment
	 */
	void setMinusSegment(MinusSegment segment);

	/**
	 * Sets the operator atr.
	 *
	 * @param operator the new operator atr
	 */
	void setOperatorAtr(OperatorAtr operator);

	/**
	 * Sets the left item.
	 *
	 * @param item the new left item
	 */
	void setLeftItem(FormulaSettingItem item);

	/**
	 * Sets the right item.
	 *
	 * @param item the new right item
	 */
	void setRightItem(FormulaSettingItem item);
}
