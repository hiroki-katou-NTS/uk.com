/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;

/**
 * The Interface EmptYearlyRetentionGetMemento.
 */
public interface EmptYearlyRetentionGetMemento {
	
	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	String getCompanyId();
	
	/**
	 * Gets the employment code.
	 *
	 * @return the employment code
	 */
	String getEmploymentCode(); 
	
	/**
	 * Gets the management category.
	 *
	 * @return the management category
	 */
	ManageDistinct getManagementCategory();
}
