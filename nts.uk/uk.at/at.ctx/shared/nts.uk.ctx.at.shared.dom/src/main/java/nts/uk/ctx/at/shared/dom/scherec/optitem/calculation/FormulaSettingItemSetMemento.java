/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.optitem.calculation;

import java.util.Optional;

/**
 * The Interface FormulaSettingItemSetMemento.
 */
public interface FormulaSettingItemSetMemento {

	/**
	 * Sets the setting method.
	 *
	 * @param method the new setting method
	 */
	void setSettingMethod(SettingMethod method);

	/**
	 * Sets the setting item order.
	 *
	 * @param order the new setting item order
	 */
	void  setSettingItemOrder(SettingItemOrder order);

	/**
	 * Sets the input value.
	 *
	 * @param value the new input value
	 */
	void setInputValue(Optional<InputValue> value);

	/**
	 * Sets the formula id.
	 *
	 * @param id the new formula id
	 */
	void setFormulaId(Optional<FormulaId> id);
}
