/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

/**
 * The Interface FlowWorkRestTimezoneGetMemento.
 */
public interface FlowWorkRestTimezoneGetMemento {
	
	/**
	 * Gets the fix rest time.
	 *
	 * @return the fix rest time
	 */
	boolean getFixRestTime();
	
	
	/**
	 * Gets the fixed rest timezone.
	 *
	 * @return the fixed rest timezone
	 */
	TimezoneOfFixedRestTimeSet getFixedRestTimezone();
	
	
	/**
	 * Gets the flow rest timezone.
	 *
	 * @return the flow rest timezone
	 */
	FlowRestTimezone getFlowRestTimezone();

}
