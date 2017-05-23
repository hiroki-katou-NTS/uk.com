/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.vacation.setting.retentionyearly;

/**
 * The Interface EmploymentSettingGetMemento.
 */
public interface EmploymentSettingGetMemento {
	
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
	 * Gets the upper limit setting.
	 *
	 * @return the upper limit setting
	 */
	UpperLimitSetting getUpperLimitSetting();
	
	/**
	 * Gets the management category.
	 *
	 * @return the management category
	 */
	ManagementCategory getManagementCategory();
}
