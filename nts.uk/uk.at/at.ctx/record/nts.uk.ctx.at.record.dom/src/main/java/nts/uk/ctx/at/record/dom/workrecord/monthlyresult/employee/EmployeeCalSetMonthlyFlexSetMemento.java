/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthlyresult.employee;

import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.AggrSettingMonthlyOfFlxNew;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;

/**
 * The Interface EmployeeCalSetMonthlyFlexSetMemento.
 */
public interface EmployeeCalSetMonthlyFlexSetMemento {

	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	void setCompanyId(CompanyId companyId);

	/**
	 * Sets the employee id.
	 *
	 * @param employeeId the new employee id
	 */
	void setEmployeeId(EmployeeId employeeId);

	/**
	 * Sets the aggr setting monthly of flx new.
	 *
	 * @param aggrSettingMonthlyOfFlxNew the new aggr setting monthly of flx new
	 */
	void setAggrSettingMonthlyOfFlxNew(AggrSettingMonthlyOfFlxNew aggrSettingMonthlyOfFlxNew);

}
