/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew;

import java.util.List;

import nts.uk.ctx.at.shared.dom.common.MonthlyTime;

/**
 * The Interface MonthStatutoryWorkingHourFlexWork.
 */
// * フレックス勤務の月間法定労働時間.
public interface MonthStatutoryWorkingHourFlexWork {

	/**
     * Gets the list statutory setting.
     *
     * @return the list statutory setting
     */
    List<MonthlyTime> getListStatutorySetting();

	/**
     * Gets the list specified setting.
     *
     * @return the list specified setting
     */
    List<MonthlyTime> getListSpecifiedSetting();
}
