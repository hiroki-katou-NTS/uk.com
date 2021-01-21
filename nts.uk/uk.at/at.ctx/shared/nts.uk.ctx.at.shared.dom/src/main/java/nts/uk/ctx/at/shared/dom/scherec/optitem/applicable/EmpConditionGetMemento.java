/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.optitem.applicable;

import java.util.List;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemNo;

/**
 * The Interface ApplicableEmpConditionGetMemento.
 */
public interface EmpConditionGetMemento {

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
	 * Gets the employment conditions.
	 *
	 * @return the employment conditions
	 */
	List<EmploymentCondition> getEmploymentConditions();
}
