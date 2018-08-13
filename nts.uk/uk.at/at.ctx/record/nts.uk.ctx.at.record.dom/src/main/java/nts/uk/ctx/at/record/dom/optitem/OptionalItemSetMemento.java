/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.optitem;

import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Interface OptionalItemSetMemento.
 */
public interface OptionalItemSetMemento {

	/**
	 * Sets the company id.
	 *
	 * @param comId the new company id
	 */
	void setCompanyId(CompanyId comId);

	/**
	 * Sets the optional item no.
	 *
	 * @param optionalItemNo the new optional item no
	 */
	void setOptionalItemNo(OptionalItemNo optionalItemNo);

	/**
	 * Sets the optional item name.
	 *
	 * @param optionalItemName the new optional item name
	 */
	void setOptionalItemName(OptionalItemName optionalItemName);

	/**
	 * Sets the optional item atr.
	 *
	 * @param optionalItemAtr the new optional item atr
	 */
	void setOptionalItemAtr(OptionalItemAtr optionalItemAtr);

	/**
	 * Sets the optional item usage atr.
	 *
	 * @param optionalItemUsageAtr the new optional item usage atr
	 */
	void setOptionalItemUsageAtr(OptionalItemUsageAtr optionalItemUsageAtr);

	/**
	 * Sets the emp condition atr.
	 *
	 * @param empConditionAtr the new emp condition atr
	 */
	void setEmpConditionAtr(EmpConditionAtr empConditionAtr);

	/**
	 * Sets the performance atr.
	 *
	 * @param performanceAtr the new performance atr
	 */
	void setPerformanceAtr(PerformanceAtr performanceAtr);

	/**
	 * Sets the calculation result range.
	 *
	 * @param calculationResultRange the new calculation result range
	 */
	void setCalculationResultRange(CalcResultRange calculationResultRange);

	/**
	 * Sets the unit.
	 *
	 * @param unit the new unit
	 */
	void setUnit(UnitOfOptionalItem unit);
}
