/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.optitem.applicable;

import java.util.List;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemNo;

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
