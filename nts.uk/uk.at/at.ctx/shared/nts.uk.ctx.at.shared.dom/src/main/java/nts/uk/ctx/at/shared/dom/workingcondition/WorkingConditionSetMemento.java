/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workingcondition;

import java.util.List;

import nts.uk.shr.com.history.DateHistoryItem;

/**
 * The Interface WorkingConditionSetMemento.
 */
public interface WorkingConditionSetMemento {

	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	void setCompanyId(String companyId);

	/**
	 * Sets the employee id.
	 *
	 * @param employeeId the new employee id
	 */
	void setEmployeeId(String employeeId);

	/**
	 * Sets the date history item.
	 *
	 * @param dateHistoryItem the new date history item
	 */
	void setDateHistoryItem(List<DateHistoryItem> dateHistoryItem);
}
