/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.usagesetting;

import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Interface UsageSettingSetMemento.
 */
public interface UsageSettingSetMemento {

	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	void setCompanyId(CompanyId companyId);

	/**
     * Sets the employment setting.
     *
     * @param employmentSetting the new employment setting
     */
    void setEmploymentSetting(UseClassification employmentSetting);

	/**
     * Sets the personal setting.
     *
     * @param personalSetting the new personal setting
     */
    void setPersonalSetting(UseClassification personalSetting);

}
