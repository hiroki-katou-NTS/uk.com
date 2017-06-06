/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.employment.statutory.worktime;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared.DeformationLaborSetting;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared.FlexSetting;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared.NormalSetting;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared.Year;

/**
 * The Interface CompanySettingGetMemento.
 */
public interface EmploymentSettingGetMemento {

	/**
	 * Gets the flex setting.
	 *
	 * @return the flex setting
	 */
	FlexSetting getFlexSetting();

	/**
	 * Gets the deformation labor setting.
	 *
	 * @return the deformation labor setting
	 */
	DeformationLaborSetting getDeformationLaborSetting();

	/**
	 * Gets the year.
	 *
	 * @return the year
	 */
	Year getYear();

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	CompanyId getCompanyId();

	/**
	 * Gets the normal setting.
	 *
	 * @return the normal setting
	 */
	NormalSetting getNormalSetting();
}
