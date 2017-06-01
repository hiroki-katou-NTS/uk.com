/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.SixtyHourExtra;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;

/**
 * The Interface SixtyHourVacationSettingSetMemento.
 */
public interface SixtyHourVacationSettingSetMemento {

	/**
	 * Sets the checks if is manage.
	 *
	 * @param isManage the new checks if is manage
	 */
	void setIsManage(ManageDistinct isManage);

	/**
	 * Sets the sixty hour extra.
	 *
	 * @param sixtyHourExtra the new sixty hour extra
	 */
	void setSixtyHourExtra(SixtyHourExtra sixtyHourExtra);

	/**
	 * Sets the digestive unit.
	 *
	 * @param digestiveUnit the new digestive unit
	 */
	void setDigestiveUnit(TimeDigestiveUnit digestiveUnit);
}
