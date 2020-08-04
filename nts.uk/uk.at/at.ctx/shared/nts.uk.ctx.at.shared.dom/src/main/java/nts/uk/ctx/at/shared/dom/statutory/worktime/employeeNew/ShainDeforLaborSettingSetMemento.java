/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.DeforLaborSettingSetMemento;

/**
 * The Interface EmpDeforLaborSettingSetMemento.
 */
public interface ShainDeforLaborSettingSetMemento extends DeforLaborSettingSetMemento {

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

}
