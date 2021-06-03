/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flexset;

import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.usecls.ApplyAtr;

/**
 * The Interface CoreTimeSettingGetMemento.
 */
public interface CoreTimeSettingGetMemento {

	/**
	 * Gets the core time sheet.
	 *
	 * @return the core time sheet
	 */
	TimeSheet getCoreTimeSheet();

	/**
	 * Gets the timesheet.
	 *
	 * @return the timesheet
	 */
	ApplyAtr getTimesheet();
	
	
	/**
	 * Gets the min work time.
	 *
	 * @return the min work time
	 */
	AttendanceTime getMinWorkTime();

	/**
	 * Gets the go out calculation
	 * @return go out calculation
	 */
	OutingCalcWithinCoreTime getGoOutCalc();
}
