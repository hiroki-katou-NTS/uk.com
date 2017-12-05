/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fixedset;

import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkTimezoneSet;
import nts.uk.ctx.at.shared.dom.worktime_old.AmPmClassification;

/**
 * The Interface FixHalfDayWorkTimezoneSetMemento.
 */
public interface FixHalfDayWorkTimezoneGetMemento {
	
	/**
	 * Gets the rest time zone.
	 *
	 * @return the rest time zone
	 */
	FixRestTimezoneSet getRestTimezone();
	
	/**
	 * Gets the work timezone.
	 *
	 * @return the work timezone
	 */
	FixedWorkTimezoneSet getWorkTimezone();
	
	/**
	 * Gets the day atr.
	 *
	 * @return the day atr
	 */
	AmPmClassification getDayAtr();
	
}
