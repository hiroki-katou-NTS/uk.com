/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workrecord;

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
	 * Sets the optional item attribute.
	 *
	 * @param optionalItemAttribute the new optional item attribute
	 */
	void setOptionalItemAttribute(OptionalItemAttribute optionalItemAttribute);

	/**
	 * Sets the optional item usage classification.
	 *
	 * @param optionalItemUsageClassification the new optional item usage classification
	 */
	void setOptionalItemUsageClassification(OptionalItemUsageClassification optionalItemUsageClassification);

	/**
	 * Sets the emp condition classification.
	 *
	 * @param empConditionClassification the new emp condition classification
	 */
	void setEmpConditionClassification(EmpConditionClassification empConditionClassification);

	/**
	 * Sets the performance classification.
	 *
	 * @param performanceClassification the new performance classification
	 */
	void setPerformanceClassification(PerformanceClassification performanceClassification);

	/**
	 * Sets the calculation result range.
	 *
	 * @param calculationResultRange the new calculation result range
	 */
	void setCalculationResultRange(CalculationResultRange calculationResultRange);
}
