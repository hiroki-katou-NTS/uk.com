/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workrecord;

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
	 * Gets the optional item attribute.
	 *
	 * @return the optional item attribute
	 */
	OptionalItemAttribute getOptionalItemAttribute();

	/**
	 * Gets the optional item usage classification.
	 *
	 * @return the optional item usage classification
	 */
	OptionalItemUsageClassification getOptionalItemUsageClassification();

	/**
	 * Gets the emp condition classification.
	 *
	 * @return the emp condition classification
	 */
	EmpConditionClassification getEmpConditionClassification();

	/**
	 * Gets the performance classification.
	 *
	 * @return the performance classification
	 */
	PerformanceClassification getPerformanceClassification();

	/**
	 * Gets the calculation result range.
	 *
	 * @return the calculation result range
	 */
	CalculationResultRange getCalculationResultRange();
}
