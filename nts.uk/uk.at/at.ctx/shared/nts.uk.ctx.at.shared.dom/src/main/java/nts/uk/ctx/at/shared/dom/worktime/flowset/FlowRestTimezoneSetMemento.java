/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

import java.util.List;

/**
 * The Interface FlowRestTimezoneSetMemento.
 */
public interface FlowRestTimezoneSetMemento {

	/**
	 * Sets the flow rest set.
	 *
	 * @param set the new flow rest set
	 */
 	void setFlowRestSet(List<FlowRestSetting> set);

	/**
	 * Sets the use here after rest set.
	 *
	 * @param val the new use here after rest set
	 */
	 void setUseHereAfterRestSet(boolean val);

	/**
	 * Sets the here after rest set.
	 *
	 * @param set the new here after rest set
	 */
	 void setHereAfterRestSet(FlowRestSetting set);
}
