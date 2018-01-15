/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.difftimeset;

import nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSettingGetMemento;

/**
 * The Interface DayOffTimezoneSettingGetMemento.
 */
public interface DayOffTimezoneSettingGetMemento extends HDWorkTimeSheetSettingGetMemento{
	
	/**
	 * Gets the checks if is update start time.
	 *
	 * @return the checks if is update start time
	 */
	boolean getIsUpdateStartTime();

}
