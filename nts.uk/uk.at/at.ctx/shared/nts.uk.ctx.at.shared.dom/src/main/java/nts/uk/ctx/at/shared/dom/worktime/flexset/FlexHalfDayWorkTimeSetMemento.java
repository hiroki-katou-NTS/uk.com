/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flexset;

import java.util.List;

import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkTimezoneSet;

/**
 * The Interface FlexHalfDayWorkTimeSetMemento.
 */
public interface FlexHalfDayWorkTimeSetMemento {

	/**
	 * Sets the rest timezone.
	 *
	 * @param restTimezone the new rest timezone
	 */
	void setRestTimezone(List<FlowWorkRestTimezone> restTimezone);
	
	
	/**
	 * Sets the work timezone.
	 *
	 * @param workTimezone the new work timezone
	 */
	void setWorkTimezone(FixedWorkTimezoneSet workTimezone);
	
	
	/**
	 * Sets the am pm atr.
	 *
	 * @param amPmAtr the new am pm atr
	 */
	void setAmPmAtr(AmPmAtr amPmAtr);
}
