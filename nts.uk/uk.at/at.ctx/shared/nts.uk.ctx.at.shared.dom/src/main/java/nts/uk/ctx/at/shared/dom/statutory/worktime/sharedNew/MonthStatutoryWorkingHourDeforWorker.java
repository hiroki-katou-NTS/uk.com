/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew;

import java.util.List;

/**
 * The Interface MonthStatutoryWorkingHourDeforWorker.
 */
// 通常・変形勤務の月間法定労働時間
public interface MonthStatutoryWorkingHourDeforWorker {
	/**
	 * Gets the list statutory setting.
	 *
	 * @return the list statutory setting
	 */
	List<MonthlyUnit> getStatutorySetting();

}
