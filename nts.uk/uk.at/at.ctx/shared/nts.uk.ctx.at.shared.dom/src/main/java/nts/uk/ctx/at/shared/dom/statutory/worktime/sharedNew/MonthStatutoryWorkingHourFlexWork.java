/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew;

import java.util.List;

/**
 * The Interface MonthStatutoryWorkingHourFlexWork.
 */
// フレックス勤務の月間法定労働時間
public interface MonthStatutoryWorkingHourFlexWork {

	/**
	 * Gets the statutory setting.
	 *
	 * @return the statutory setting
	 */
    List<MonthlyUnit> getStatutorySetting();

	/**
	 * Gets the specified setting.
	 *
	 * @return the specified setting
	 */
    List<MonthlyUnit> getSpecifiedSetting();
}
