/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew;

import java.util.List;

import nts.uk.ctx.at.shared.dom.common.Year;

/**
 * The Interface ComFlexSettingGetMemento.
 */
public interface FlexSettingGetMemento {

	/**
	 * Gets the year.
	 *
	 * @return the year
	 */
	Year getYear();

	/**
	 * Gets the statutory setting.
	 *
	 * @return the statutory setting
	 */
	List<MonthlyUnit> getStatutorySetting();

	/**
	 * Gets the specified setting.
	 *
	 * @return the specified setting
	 */
	List<MonthlyUnit> getSpecifiedSetting();
	
	/**
	 * Gets the week avarage setting.
	 * 
	 * @return the week average setting.
	 */
	List<MonthlyUnit> getWeekAveSetting();
}
