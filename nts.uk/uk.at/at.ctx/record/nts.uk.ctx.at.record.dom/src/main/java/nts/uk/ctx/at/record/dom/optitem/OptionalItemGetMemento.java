/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.optitem;

import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Interface OptionalItemGetMemento.
 */
public interface OptionalItemGetMemento {

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	CompanyId getCompanyId();

	/**
	 * Gets the optional item no.
	 *
	 * @return the optional item no
	 */
	OptionalItemNo getOptionalItemNo();

	/**
	 * Gets the optional item name.
	 *
	 * @return the optional item name
	 */
	OptionalItemName getOptionalItemName();

	/**
	 * Gets the optional item atr.
	 *
	 * @return the optional item atr
	 */
	OptionalItemAtr getOptionalItemAtr();

	/**
	 * Gets the optional item usage atr.
	 *
	 * @return the optional item usage atr
	 */
	OptionalItemUsageAtr getOptionalItemUsageAtr();

	/**
	 * Gets the emp condition atr.
	 *
	 * @return the emp condition atr
	 */
	EmpConditionAtr getEmpConditionAtr();

	/**
	 * Gets the performance atr.
	 *
	 * @return the performance atr
	 */
	PerformanceAtr getPerformanceAtr();

	/**
	 * Gets the calculation result range.
	 *
	 * @return the calculation result range
	 */
	CalcResultRange getCalculationResultRange();

	/**
	 * Gets the unit.
	 *
	 * @return the unit
	 */
	UnitOfOptionalItem getUnit();
}
