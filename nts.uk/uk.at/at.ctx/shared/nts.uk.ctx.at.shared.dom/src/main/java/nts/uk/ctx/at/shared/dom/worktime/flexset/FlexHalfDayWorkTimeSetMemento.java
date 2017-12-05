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
 * The Interface FlexHalfDayWorkTimeSetMemento.
 */
public interface FlexHalfDayWorkTimeSetMemento {

	/**
	 * Sets the lst rest timezone.
	 *
	 * @param lstRestTimezone the new lst rest timezone
	 */
	void setLstRestTimezone(List<FlowWorkRestTimezone> lstRestTimezone);
	
	
	/**
	 * Sets the work timezone.
	 *
	 * @param workTimezone the new work timezone
	 */
	void setWorkTimezone(FixedWorkTimezoneSet workTimezone);
	
	
	/**
	 * Sets the ampm atr.
	 *
	 * @param ampmAtr the new ampm atr
	 */
	void setAmpmAtr(AmPmAtr ampmAtr);
}
