/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WorkingTimeSettingNew;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

/**
 * The Interface EmploymentRegularWorkHourSetMemento.
 */
public interface EmploymentRegularWorkHourSetMemento {

	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	void setCompanyId(CompanyId companyId);

	/**
	 * Sets the employment code.
	 *
	 * @param employmentCode the new employment code
	 */
	void setEmploymentCode(EmploymentCode employmentCode);

	/**
	 * Sets the working time setting new.
	 *
	 * @param workingTimeSettingNew the new working time setting new
	 */
	void setWorkingTimeSettingNew(WorkingTimeSettingNew workingTimeSettingNew);

}
