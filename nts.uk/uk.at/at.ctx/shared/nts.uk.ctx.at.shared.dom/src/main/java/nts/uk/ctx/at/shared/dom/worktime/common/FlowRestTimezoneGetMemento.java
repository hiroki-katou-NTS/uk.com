/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import java.util.List;

/**
 * The Interface FlowRestTimezoneGetMemento.
 */
public interface FlowRestTimezoneGetMemento {

	/**
 	 * Gets the flow rest set.
 	 *
 	 * @return the flow rest set
 	 */
 	List<FlowRestSetting> getFlowRestSet();

	/**
	 * Gets the use here after rest set.
	 *
	 * @return the use here after rest set
	 */
	 boolean getUseHereAfterRestSet();

	/**
	 * Gets the here after rest set.
	 *
	 * @return the here after rest set
	 */
	 FlowRestSetting getHereAfterRestSet();
}
