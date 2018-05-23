/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.request.dom.vacation.history;

import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Interface PlanVacationHistorySetMemento.
 */
public interface PlanVacationHistorySetMemento {
	
	/**
	 * Sets the history id.
	 *
	 * @param historyId the new history id
	 */
	public void setHistoryId(String historyId);
	
	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	public void setCompanyId(String companyId);
	
	/**
	 * Sets the work type code.
	 *
	 * @param workTypeCode the new work type code
	 */
	public void setWorkTypeCode(String workTypeCode);
	
	/**
	 * Sets the max day.
	 *
	 * @param maxDay the new max day
	 */
	public void setMaxDay(Integer maxDay);
	
	/**
	 * Sets the period.
	 *
	 * @param period the new period
	 */
	public void setPeriod(DatePeriod period);

}
