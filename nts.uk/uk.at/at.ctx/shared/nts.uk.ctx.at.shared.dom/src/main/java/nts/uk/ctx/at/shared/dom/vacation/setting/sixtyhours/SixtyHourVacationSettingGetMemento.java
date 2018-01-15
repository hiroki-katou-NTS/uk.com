/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.SixtyHourExtra;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;

/**
 * The Interface SixtyHourVacationSettingGetMemento.
 */
public interface SixtyHourVacationSettingGetMemento {

	/**
	 * Gets the checks if is manage.
	 *
	 * @return the checks if is manage
	 */
	ManageDistinct getIsManage();

	/**
	 * Gets the sixty hour extra.
	 *
	 * @return the sixty hour extra
	 */
	SixtyHourExtra getSixtyHourExtra();

	/**
	 * Gets the digestive unit.
	 *
	 * @return the digestive unit
	 */
	TimeDigestiveUnit getDigestiveUnit();

}
