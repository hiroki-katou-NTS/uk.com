/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;

/**
 * The Interface CompensatoryLeaveComSetMemento.
 */
public interface CompensatoryLeaveComSetMemento {
	
	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	void setCompanyId(String companyId);
	
	/**
	 * Sets the checks if is managed.
	 *
	 * @param isManaged the new checks if is managed
	 */
	void setIsManaged (ManageDistinct isManaged);
	
	/**
	 * Sets the normal vacation setting.
	 *
	 * @param normalVacationSetting the new normal vacation setting
	 */
	void setNormalVacationSetting(NormalVacationSetting normalVacationSetting);
	
	/**
	 * Sets the occurrence vacation setting.
	 *
	 * @param occurrenceVacationSetting the new occurrence vacation setting
	 */
	void setOccurrenceVacationSetting(OccurrenceVacationSetting occurrenceVacationSetting);
}
