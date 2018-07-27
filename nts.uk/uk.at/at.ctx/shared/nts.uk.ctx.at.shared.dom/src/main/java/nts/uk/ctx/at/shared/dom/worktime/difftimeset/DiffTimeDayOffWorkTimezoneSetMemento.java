/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.difftimeset;

import java.util.List;

import nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSetting;

/**
 * The Interface DiffTimeDayOffWorkTimezoneSetMemento.
 */
public interface DiffTimeDayOffWorkTimezoneSetMemento {
	
	/**
	 * Sets the rest timezone.
	 *
	 * @param restTimezone the new rest timezone
	 */
	public void setRestTimezone(DiffTimeRestTimezone restTimezone);

	/**
	 * Sets the work timezones.
	 *
	 * @param workTimezone the new work timezones
	 */
	public void setWorkTimezones(List<HDWorkTimeSheetSetting> workTimezone);
}
