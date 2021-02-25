/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;

/**
 * The Interface EmptYearlyRetentionSetMemento.
 */
public interface EmptYearlyRetentionSetMemento {
	
	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	void setCompanyId(String companyId);
	
	/**
	 * Sets the employment code.
	 *
	 * @param employmentCode the new employment code
	 */
	void setEmploymentCode(String employmentCode); 
	
	/**
	 * Sets the management category.
	 *
	 * @param managementCategory the new management category
	 */
	void setManagementCategory(ManageDistinct managementCategory);
}
