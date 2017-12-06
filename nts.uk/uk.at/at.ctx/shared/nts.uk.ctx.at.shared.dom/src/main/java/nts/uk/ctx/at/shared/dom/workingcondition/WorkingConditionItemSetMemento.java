package nts.uk.ctx.at.shared.dom.workingcondition;

import java.util.List;


/**
 * The Interface WorkingConditionSetMemento.
 */
public interface WorkingConditionItemSetMemento {
	
	/**
	 * Sets the employee id.
	 *
	 * @param employeeId the new employee id
	 */
	public void setEmployeeId(String employeeId);
	
	/**
	 * Sets the date History Item
	 *
	 * @param dateHistoryItem the new date history item
	 */
	public void setDateHistoryItem(List<DateHistoryItem> dateHistoryItem);
}
