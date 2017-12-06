/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fixedset;

import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkTimezoneSet;
import nts.uk.ctx.at.shared.dom.worktime_old.AmPmClassification;

/**
 * The Interface FixHalfDayWorkTimezoneGetMemento.
 */
public interface FixHalfDayWorkTimezoneSetMemento {
	
	/**
	 * Sets the rest time zone.
	 *
	 * @param restTimeZone the new rest time zone
	 */
	void setRestTimezone(FixRestTimezoneSet restTimezone);

	/**
	 * Sets the work timezone.
	 *
	 * @param workTimezone the new work timezone
	 */
	void setWorkTimezone(FixedWorkTimezoneSet workTimezone);

	/**
	 * Sets the day atr.
	 *
	 * @param dayAtr the new day atr
	 */
	void setDayAtr(AmPmClassification dayAtr);
	
}
