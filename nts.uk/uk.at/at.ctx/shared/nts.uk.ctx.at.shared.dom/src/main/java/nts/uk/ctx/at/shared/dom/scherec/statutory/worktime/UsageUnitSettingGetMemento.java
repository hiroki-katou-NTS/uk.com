/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.statutory.worktime;

import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Interface UsageUnitSettingGetMemento.
 */
public interface UsageUnitSettingGetMemento {

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	CompanyId getCompanyId();
	
	/**
	 * Gets the employee.
	 *
	 * @return the employee
	 */
	boolean getEmployee();

	/**
	 * Gets the work place.
	 *
	 * @return the work place
	 */
	boolean getWorkPlace();

	/**
	 * Gets the employment.
	 *
	 * @return the employment
	 */
	boolean getEmployment();

}
