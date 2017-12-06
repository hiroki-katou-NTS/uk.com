/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flexset;

import java.util.List;

import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkTimezoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezone;

/**
 * The Interface FlexHalfDayWorkTimeGetMemento.
 */
public interface FlexHalfDayWorkTimeGetMemento {

	/**
	 * Gets the lst rest timezone.
	 *
	 * @return the lst rest timezone
	 */
	List<FlowWorkRestTimezone> getLstRestTimezone();
	
	
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
