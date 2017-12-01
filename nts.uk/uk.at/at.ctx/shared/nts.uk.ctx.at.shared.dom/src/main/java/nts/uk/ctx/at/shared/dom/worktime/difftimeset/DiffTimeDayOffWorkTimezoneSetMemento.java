/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.difftimeset;

import java.util.List;

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
	 * Sets the work timezone.
	 *
	 * @param workTimezone the new work timezone
	 */
	public void setWorkTimezone(List<DayOffTimezoneSetting> workTimezone);
}
