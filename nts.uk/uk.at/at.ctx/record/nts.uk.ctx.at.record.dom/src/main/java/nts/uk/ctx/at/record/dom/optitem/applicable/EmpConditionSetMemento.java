/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.optitem.applicable;

import java.util.List;

import nts.uk.ctx.at.record.dom.optitem.OptionalItemNo;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Interface ApplicableEmpConditionSetMemento.
 */
public interface EmpConditionSetMemento {

	/**
	 * Sets the company id.
	 *
	 * @param comId the new company id
	 */
	void setCompanyId(CompanyId comId);

	/**
	 * Sets the optional item no.
	 *
	 * @param optNo the new optional item no
	 */
	void setOptionalItemNo(OptionalItemNo optNo);

	/**
	 * Sets the emp conditions.
	 *
	 * @param empConditions the emp conditions
	 */
	void setEmpConditions(List<EmploymentCondition> empConditions);
}
