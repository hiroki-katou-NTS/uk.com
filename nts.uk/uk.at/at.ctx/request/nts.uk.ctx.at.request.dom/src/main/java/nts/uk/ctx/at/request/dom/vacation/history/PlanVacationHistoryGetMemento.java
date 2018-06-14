/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.request.dom.vacation.history;

import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Interface PlanVacationHistoryGetMemento.
 */
public interface PlanVacationHistoryGetMemento {

	/**
	 * Gets the history id.
	 *
	 * @return the history id
	 */
	public String getHistoryId();
	
	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	public String getCompanyId();
	
	/**
	 * Gets the work type code.
	 *
	 * @return the work type code
	 */
	public String getWorkTypeCode();
	
	/**
	 * Gets the max day.
	 *
	 * @return the max day
	 */
	public OptionalMaxDay getMaxDay();
	
	/**
	 * Gets the period.
	 *
	 * @return the period
	 */
	public DatePeriod getPeriod();
}
