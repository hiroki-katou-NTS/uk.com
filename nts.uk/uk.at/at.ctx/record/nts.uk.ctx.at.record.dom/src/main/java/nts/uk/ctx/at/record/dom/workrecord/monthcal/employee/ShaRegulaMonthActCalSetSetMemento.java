/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthcal.employee;

import nts.uk.ctx.at.record.dom.workrecord.monthcal.RegularWorkTimeAggrSet;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;

/**
 * The Interface EmployeeRegularSetMonthlyActualSetMemento.
 */
public interface ShaRegulaMonthActCalSetSetMemento {

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
	 * Sets the legal aggr set of reg new.
	 *
	 * @param legalAggrSetOfRegNew the new legal aggr set of reg new
	 */
	void setAggrSetting(RegularWorkTimeAggrSet legalAggrSetOfRegNew);

}
