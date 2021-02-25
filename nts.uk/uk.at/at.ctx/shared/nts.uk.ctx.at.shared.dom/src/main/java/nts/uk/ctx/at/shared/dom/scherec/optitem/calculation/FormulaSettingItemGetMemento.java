/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.optitem.calculation;

import java.util.Optional;

/**
 * The Interface FormulaSettingItemGetMemento.
 */
public interface FormulaSettingItemGetMemento {
	/**
	 * Gets the setting item order.
	 *
	 * @return the setting item order
	 */
	SettingItemOrder getSettingItemOrder();

	/**
	 * Gets the setting method.
	 *
	 * @return the setting method
	 */
	SettingMethod getSettingMethod();


	/**
	 * Gets the input value.
	 *
	 * @return the input value
	 */
	Optional<InputValue> getInputValue();

	/**
	 * Gets the formula id.
	 *
	 * @return the formula id
	 */
	Optional<FormulaId> getFormulaId();
}
