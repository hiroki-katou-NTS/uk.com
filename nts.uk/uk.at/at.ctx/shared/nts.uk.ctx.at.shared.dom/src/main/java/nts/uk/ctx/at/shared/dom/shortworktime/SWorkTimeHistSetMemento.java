/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.shortworktime;

import java.util.List;

import nts.uk.shr.com.history.DateHistoryItem;

/**
 * The Interface SWorkTimeHistSetMemento.
 */
public interface SWorkTimeHistSetMemento {
	
	/**
	 * Sets the company id.
	 *
	 * @param cid the new company id
	 */
	void setCompanyId(String cid);

	/**
	 * Sets the employee id.
	 *
	 * @param empId the new employee id
	 */
	void setEmployeeId(String empId);
	
	/**
	 * Sets the history items.
	 *
	 * @param historyItems the new history items
	 */
	void setHistoryItems(List<DateHistoryItem> historyItems);
}
