/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flexset;

import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkTimezoneSet;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestTimezone;

/**
 * The Interface FlexHalfDayWorkTimeGetMemento.
 */
public interface FlexHalfDayWorkTimeGetMemento {

	/**
	 * Gets the rest timezone.
	 *
	 * @return the rest timezone
	 */
	FlowWorkRestTimezone getRestTimezone();

	/**
	 * Gets the work timezone.
	 *
	 * @return the work timezone
	 */
	FixedWorkTimezoneSet getWorkTimezone();

	/**
	 * Gets the ampm atr.
	 *
	 * @return the ampm atr
	 */
	AmPmAtr getAmpmAtr();
}
