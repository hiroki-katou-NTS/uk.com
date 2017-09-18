/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.optitem.calculation;

import java.util.List;

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
	 * Sets the formula setting items.
	 *
	 * @param listItem the new formula setting items
	 */
	void setFormulaSettingItems(List<FormulaSettingItem> listItem);
}
