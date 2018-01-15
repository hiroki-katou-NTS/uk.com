/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.difftimeset;

import nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSettingSetMemento;

/**
 * The Interface DayOffTimezoneSettingSetMemento.
 */
public interface DayOffTimezoneSettingSetMemento extends HDWorkTimeSheetSettingSetMemento{

	/**
	 * Sets the checks if is update start time.
	 *
	 * @param isUpdateStartTime the new checks if is update start time
	 */
	void setIsUpdateStartTime(boolean isUpdateStartTime);
}
