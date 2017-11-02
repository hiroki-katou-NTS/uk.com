/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.shortworktime;

import nts.uk.shr.com.history.DateHistoryItem;

/**
 * The Interface SWorkTimeHistGetMemento.
 */
public interface SWorkTimeHistGetMemento {

	/**
	 * Gets the employee id.
	 *
	 * @return the employee id
	 */
	String getEmployeeId();
	
	/**
	 * Gets the history item.
	 *
	 * @return the history item
	 */
	DateHistoryItem getHistoryItem();
}
