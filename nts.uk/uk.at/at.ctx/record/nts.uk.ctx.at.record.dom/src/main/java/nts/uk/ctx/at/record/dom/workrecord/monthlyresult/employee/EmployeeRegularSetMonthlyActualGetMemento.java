/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthlyresult.employee;

import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.LegalAggrSetOfRegNew;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;

/**
 * The Interface EmployeeRegularSetMonthlyActualGetMemento.
 */
public interface EmployeeRegularSetMonthlyActualGetMemento {

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	CompanyId getCompanyId();

	/**
	 * Gets the employee id.
	 *
	 * @return the employee id
	 */
	EmployeeId getEmployeeId();

	/**
	 * Gets the legal aggr set of reg new.
	 *
	 * @return the legal aggr set of reg new
	 */
	LegalAggrSetOfRegNew getLegalAggrSetOfRegNew();

}
