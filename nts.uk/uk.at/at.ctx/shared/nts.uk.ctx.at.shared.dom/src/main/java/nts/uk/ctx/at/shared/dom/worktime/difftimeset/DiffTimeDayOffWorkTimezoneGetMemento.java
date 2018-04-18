/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.difftimeset;

import java.util.List;

import nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSetting;

/**
 * The Interface DiffTimeDayOffWorkTimezoneGetMemento.
 */
public interface DiffTimeDayOffWorkTimezoneGetMemento {

	/**
	 * Gets the rest timezone.
	 *
	 * @return the rest timezone
	 */
	public DiffTimeRestTimezone getRestTimezone() ;

	/**
	 * Gets the work timezones.
	 *
	 * @return the work timezones
	 */
	public List<HDWorkTimeSheetSetting> getWorkTimezones();
}
