/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.statutory.worktime;

import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Interface UsageUnitSettingSetMemento.
 */
public interface UsageUnitSettingSetMemento {
	
	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	void setCompanyId(CompanyId companyId);

	/**
	 * Sets the employee.
	 *
	 * @param employee the new employee
	 */
	void setEmployee(boolean employee);

	/**
	 * Sets the work place.
	 *
	 * @param workPlace the new work place
	 */
	void setWorkPlace(boolean workPlace);

	/**
	 * Sets the employment.
	 *
	 * @param employment the new employment
	 */
	void setEmployment(boolean employment);

}
