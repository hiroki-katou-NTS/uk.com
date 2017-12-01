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
 * The Interface FlexHalfDayWorkTimeGetMemento.
 */
public interface FlexHalfDayWorkTimeGetMemento {

	/**
	 * Gets the rest timezone.
	 *
	 * @return the rest timezone
	 */
	List<FlowWorkRestTimezone> getRestTimezone();
	
	
	/**
	 * Gets the work timezone.
	 *
	 * @return the work timezone
	 */
	FixedWorkTimezoneSet getWorkTimezone();
	
	
	/**
	 * Gets the am pm atr.
	 *
	 * @return the am pm atr
	 */
	AmPmAtr getAmPmAtr();
}
