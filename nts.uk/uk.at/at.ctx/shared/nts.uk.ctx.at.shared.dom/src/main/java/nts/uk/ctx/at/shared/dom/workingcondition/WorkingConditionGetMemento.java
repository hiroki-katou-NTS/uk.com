/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workingcondition;

import java.util.List;

// TODO: Auto-generated Javadoc
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
	 * Gets the history id.
	 *
	 * @return the history id
	 */
	String getHistoryId();

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
