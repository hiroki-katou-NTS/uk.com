/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.vacation.setting.retentionyearly;

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
	 * Gets the can add to cumulation yearly as normal work day.
	 *
	 * @return the can add to cumulation yearly as normal work day
	 */
	Boolean getCanAddToCumulationYearlyAsNormalWorkDay();
}
