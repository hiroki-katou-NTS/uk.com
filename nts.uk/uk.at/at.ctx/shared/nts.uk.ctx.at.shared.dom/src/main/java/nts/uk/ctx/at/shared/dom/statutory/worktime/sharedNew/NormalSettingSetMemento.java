/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew;

import java.util.List;

import nts.uk.ctx.at.shared.dom.common.Year;

/**
 * The Interface ComNormalSettingSetMemento.
 */
public interface NormalSettingSetMemento {

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

}
