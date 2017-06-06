/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;

/**
 * The Interface CompensatoryLeaveComGetMemento.
 */
public interface CompensatoryLeaveComGetMemento {
	
	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	String getCompanyId();
	
	/**
	 * Gets the checks if is managed.
	 *
	 * @return the checks if is managed
	 */
	ManageDistinct getIsManaged();
	
	/**
	 * Gets the normal vacation setting.
	 *
	 * @return the normal vacation setting
	 */
	NormalVacationSetting getNormalVacationSetting();
	
	/**
	 * Gets the occurrence vacation setting.
	 *
	 * @return the occurrence vacation setting
	 */
	OccurrenceVacationSetting getOccurrenceVacationSetting();
}
