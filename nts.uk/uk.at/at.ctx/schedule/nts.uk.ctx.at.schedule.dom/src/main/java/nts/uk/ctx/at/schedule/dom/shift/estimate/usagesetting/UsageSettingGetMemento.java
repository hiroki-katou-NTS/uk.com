/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.usagesetting;

import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Interface UsageSettingGetMemento.
 */
public interface UsageSettingGetMemento {

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	CompanyId getCompanyId();

	/**
	 * Gets the employment setting.
	 *
	 * @return the employment setting
	 */
	UseClassification getEmploymentSetting();

	/**
	 * Gets the personal setting.
	 *
	 * @return the personal setting
	 */
	UseClassification getPersonalSetting();

}
