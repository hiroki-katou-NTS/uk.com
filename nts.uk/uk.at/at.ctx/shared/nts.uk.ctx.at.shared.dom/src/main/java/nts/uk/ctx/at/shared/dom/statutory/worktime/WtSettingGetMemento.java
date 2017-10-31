/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.DeformationLaborSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.FlexSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.NormalSetting;

/**
 * The Interface WtSettingGetMemento.
 */
public interface WtSettingGetMemento {

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
