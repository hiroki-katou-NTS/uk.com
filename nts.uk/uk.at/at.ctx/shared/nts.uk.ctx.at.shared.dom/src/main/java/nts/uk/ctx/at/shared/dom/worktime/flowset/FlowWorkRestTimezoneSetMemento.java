/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

import nts.uk.ctx.at.shared.dom.worktime.common.TimezoneOfFixedRestTimeSet;

/**
 * The Interface FlowWorkRestTimezoneSetMemeto.
 */
public interface FlowWorkRestTimezoneSetMemento {
	
	/**
	 * Sets the fix rest time.
	 *
	 * @param fixRestTime the new fix rest time
	 */
	void setFixRestTime(boolean fixRestTime);
	
	
	/**
	 * Sets the fixed rest timezone.
	 *
	 * @param fixedRestTimezone the new fixed rest timezone
	 */
	void setFixedRestTimezone(TimezoneOfFixedRestTimeSet fixedRestTimezone);
	
	
	/**
	 * Sets the flow rest timezone.
	 *
	 * @param flowRestTimezone the new flow rest timezone
	 */
	void setFlowRestTimezone(FlowRestTimezone flowRestTimezone);

}
