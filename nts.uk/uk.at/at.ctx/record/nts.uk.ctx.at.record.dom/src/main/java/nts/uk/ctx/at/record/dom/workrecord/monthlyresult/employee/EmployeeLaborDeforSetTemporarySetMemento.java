/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthlyresult.employee;

import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.LegalAggrSetOfIrgNew;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;

/**
 * The Interface EmployeeLaborDeforSetTemporarySetMemento.
 */
public interface EmployeeLaborDeforSetTemporarySetMemento {

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
	 * Sets the legal aggr set of irg new.
	 *
	 * @param legalAggrSetOfIrgNew the new legal aggr set of irg new
	 */
	void setLegalAggrSetOfIrgNew(LegalAggrSetOfIrgNew legalAggrSetOfIrgNew);

}
