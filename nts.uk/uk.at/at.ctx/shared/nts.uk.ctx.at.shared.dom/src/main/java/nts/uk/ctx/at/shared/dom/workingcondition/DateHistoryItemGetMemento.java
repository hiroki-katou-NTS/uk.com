/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workingcondition;

import nts.uk.shr.com.time.calendar.period.DatePeriod;


/**
 * The Interface DateHistoryItemGetMemento.
 */
public interface DateHistoryItemGetMemento {

	/**
	 * Gets the history id.
	 *
	 * @return the history id
	 */
	public String getHistoryId();

	/**
	 * Gets the period.
	 *
	 * @return the period
	 */
	public DatePeriod getPeriod();
}
