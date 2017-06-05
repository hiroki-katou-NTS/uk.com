/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly;

/**
 * The Interface RetentionYearlySettingGetMemento.
 */
public interface RetentionYearlySettingGetMemento {
	
	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	String getCompanyId();
	
	/**
	 * Gets the upper limit setting.
	 *
	 * @return the upper limit setting
	 */
	UpperLimitSetting getUpperLimitSetting();
	
	/**
	 * Gets the leave as work days.
	 *
	 * @return the leave as work days
	 */
	Boolean getLeaveAsWorkDays();
}
