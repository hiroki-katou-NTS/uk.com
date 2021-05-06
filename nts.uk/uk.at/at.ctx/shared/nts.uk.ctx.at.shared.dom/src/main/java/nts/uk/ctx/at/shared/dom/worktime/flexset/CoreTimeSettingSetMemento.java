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
public interface CoreTimeSettingSetMemento {

	/**
	 * Sets the core time sheet.
	 *
	 * @param coreTimeSheet the new core time sheet
	 */
	void setCoreTimeSheet(TimeSheet coreTimeSheet);
	
	
	/**
	 * Sets the timesheet.
	 *
	 * @param timesheet the new timesheet
	 */
	void setTimesheet(ApplyAtr timesheet);
	
	
	/**
	 * Sets the min work time.
	 *
	 * @param minWorkTime the new min work time
	 */
	void setMinWorkTime(AttendanceTime minWorkTime);

	/**
	 * Sets the go out calc
	 *
	 * @param goOutCalc the go out calc
	 */
	void setGoOutCalc(OutingCalcWithinCoreTime goOutCalc);
}
