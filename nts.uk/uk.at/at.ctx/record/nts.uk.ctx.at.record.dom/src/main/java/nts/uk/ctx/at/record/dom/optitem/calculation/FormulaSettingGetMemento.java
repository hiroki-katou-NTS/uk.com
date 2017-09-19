/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.optitem.calculation;

import java.util.List;

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
	 * Gets the formula setting items.
	 *
	 * @return the formula setting items
	 */
	List<FormulaSettingItem> getFormulaSettingItems();
}
