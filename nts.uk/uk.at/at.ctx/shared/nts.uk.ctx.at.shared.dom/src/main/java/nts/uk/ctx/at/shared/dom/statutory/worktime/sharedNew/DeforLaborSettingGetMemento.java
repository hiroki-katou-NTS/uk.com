/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew;

import java.util.List;

import nts.uk.ctx.at.shared.dom.common.Year;

/**
 * The Interface ComDeformationLaborSettingGetMemento.
 */
public interface DeforLaborSettingGetMemento {

	/**
	 * Gets the year.
	 *
	 * @return the year
	 */
	Year getYear();

	/**
	 * Gets the list statutory setting.
	 *
	 * @return the list statutory setting
	 */
	List<MonthlyUnit> getStatutorySetting();

}
