/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.workplace;

/**
 * The Interface WorkplaceHistoryGetMemento.
 */
public interface WorkplaceHistoryGetMemento {

	/**
	 * Gets the history id.
	 *
	 * @return the history id
	 */
	public HistoryId getHistoryId();

	/**
	 * Gets the workplace id.
	 *
	 * @return the workplace id
	 */
	public WorkplaceId getWorkplaceId();

	/**
	 * Gets the period.
	 *
	 * @return the period
	 */
	public Period getPeriod();
}
