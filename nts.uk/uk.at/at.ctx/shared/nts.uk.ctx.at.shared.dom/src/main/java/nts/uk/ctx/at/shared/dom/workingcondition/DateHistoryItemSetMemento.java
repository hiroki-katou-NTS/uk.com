/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workingcondition;

import nts.arc.time.calendar.period.DatePeriod;


/**
 * The Interface DateHistoryItemSetMemento.
 */
public interface DateHistoryItemSetMemento {

	/**
	 * Sets the history id.
	 *
	 * @param historyId the new history id
	 */
	public void setHistoryId(String historyId);

	/**
	 * Sets the period.
	 *
	 * @param period the new period
	 */
	public void setPeriod(DatePeriod period);
}
