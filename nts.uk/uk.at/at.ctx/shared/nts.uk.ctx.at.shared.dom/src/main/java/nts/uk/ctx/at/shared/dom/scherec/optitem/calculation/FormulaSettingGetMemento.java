/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.optitem.calculation;

/**
 * The Interface FormulaSettingGetMemento.
 */
public interface FormulaSettingGetMemento {

	/**
	 * Gets the minus segment.
	 *
	 * @return the minus segment
	 */
	MinusSegment getMinusSegment();

	/**
	 * Gets the operator atr.
	 *
	 * @return the operator atr
	 */
	OperatorAtr getOperatorAtr();

	/**
	 * Gets the left item.
	 *
	 * @return the left item
	 */
	FormulaSettingItem getLeftItem();

	/**
	 * Gets the right item.
	 *
	 * @return the right item
	 */
	FormulaSettingItem getRightItem();
}
