package nts.uk.ctx.at.shared.dom.workingcondition;

import java.util.List;


/**
 * The Interface WorkingConditionGetMemento.
 */
public interface WorkingConditionGetMemento {
	
	/**
	 * Gets the employee id.
	 *
	 * @return the employee id
	 */
	public String getEmployeeId();
	
	/**
	 * Gets the date history item.
	 *
	 * @return the date history item
	 */
	public List<DateHistoryItem> getDateHistoryItem();
}
