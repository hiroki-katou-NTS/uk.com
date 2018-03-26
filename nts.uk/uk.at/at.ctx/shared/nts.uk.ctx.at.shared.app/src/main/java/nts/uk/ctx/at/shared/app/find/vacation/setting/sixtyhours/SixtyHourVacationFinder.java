/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.vacation.setting.sixtyhours;

import nts.uk.ctx.at.shared.app.find.vacation.setting.sixtyhours.dto.SixtyHourVacationSettingCheckDto;
import nts.uk.ctx.at.shared.app.find.vacation.setting.sixtyhours.dto.SixtyHourVacationSettingDto;

/**
 * The Interface 60HourVacationFinder.
 */
public interface SixtyHourVacationFinder {

	/**
	 * Find com setting.
	 *
	 * @return the subst vacation setting dto
	 */
	SixtyHourVacationSettingDto findComSetting();
	
	/**
	 * Check manange setting.
	 *
	 * @return the sixty hour vacation setting check dto
	 */
	SixtyHourVacationSettingCheckDto checkManangeSetting();
}
