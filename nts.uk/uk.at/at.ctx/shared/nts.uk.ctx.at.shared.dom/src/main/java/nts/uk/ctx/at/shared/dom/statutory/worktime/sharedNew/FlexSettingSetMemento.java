/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew;

import java.util.List;

import nts.uk.ctx.at.shared.dom.common.Year;

/**
 * The Interface ComFlexSettingSetMemento.
 */
public interface FlexSettingSetMemento {

	/**
	 * Sets the year.
	 *
	 * @param year the new year
	 */
	void setYear(Year year);

	/**
	 * Sets the statutory setting.
	 *
	 * @param statutorySetting the new statutory setting
	 */
	void setStatutorySetting(List<MonthlyUnit> statutorySetting);

	/**
	 * Sets the specified setting.
	 *
	 * @param specifiedSetting the new specified setting
	 */
	void setSpecifiedSetting(List<MonthlyUnit> specifiedSetting);

	/**
	 * Sets the week average setting.
	 * 
	 * @param weekAveSetting the new week average setting
	 */
	void setWeekAveSetting(List<MonthlyUnit> weekAveSetting);
}
