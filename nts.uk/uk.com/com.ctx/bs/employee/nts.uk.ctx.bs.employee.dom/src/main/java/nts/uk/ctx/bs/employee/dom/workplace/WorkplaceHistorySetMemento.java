/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.workplace;

/**
 * The Interface WorkplaceHistorySetMemento.
 */
public interface WorkplaceHistorySetMemento {

	/**
	 * Sets the history id.
	 *
	 * @param historyId the new history id
	 */
	public void setHistoryId(HistoryId historyId);

	/**
	 * Sets the workplace id.
	 *
	 * @param workplaceId the new workplace id
	 */
	public void setWorkplaceId(WorkplaceId workplaceId);

	/**
	 * Sets the period.
	 *
	 * @param period the new period
	 */
	public void setPeriod(Period period);
}
