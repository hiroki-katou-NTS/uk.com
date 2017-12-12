/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workingcondition;

import java.util.List;

import nts.uk.shr.com.history.DateHistoryItem;

/**
 * The Interface WorkingConditionGetMemento.
 */
public interface WorkingConditionGetMemento {

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	String getCompanyId();

	/**
	 * Gets the employee id.
	 *
	 * @return the employee id
	 */
	String getEmployeeId();

	/**
	 * Gets the date history item.
	 *
	 * @return the date history item
	 */
	List<DateHistoryItem> getDateHistoryItem();
}
