/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.vacation.setting.retentionyearly;

/**
 * The Interface RetentionYearlySettingSetMemento.
 */
public interface RetentionYearlySettingSetMemento {
	
	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	void setCompanyId(String companyId);
	
	/**
	 * Sets the upper limit setting.
	 *
	 * @param upperLimitSetting the new upper limit setting
	 */
	void setUpperLimitSetting(UpperLimitSetting upperLimitSetting);
	
	/**
	 * Sets the can add to cumulation yearly as normal work day.
	 *
	 * @param canAddToCumulationYearlyAsNormalWorkDay the new can add to cumulation yearly as normal work day
	 */
	void setcanAddToCumulationYearlyAsNormalWorkDay(Boolean canAddToCumulationYearlyAsNormalWorkDay);
}
