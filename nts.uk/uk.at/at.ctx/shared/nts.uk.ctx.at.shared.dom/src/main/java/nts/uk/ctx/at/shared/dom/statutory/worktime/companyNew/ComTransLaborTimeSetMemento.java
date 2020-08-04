/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WorkingTimeSetting;

/**
 * The Interface CompanyTransLaborHourSetMemento.
 */
public interface ComTransLaborTimeSetMemento {

	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	void setCompanyId(CompanyId companyId);

	/**
	 * Sets the working time setting new.
	 *
	 * @param workingTimeSettingNew the new working time setting new
	 */
	void setWorkingTimeSet(WorkingTimeSetting workingTimeSettingNew);
}
