/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.workplace_old;

import java.util.List;

import nts.uk.ctx.bs.employee.dom.common.CompanyId;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Interface WorkHistoryGetMemento.
 */
public interface WorkPlaceHistoryGetMemento {
	
	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	CompanyId getCompanyId();

	/**
	 * Gets the history id.
	 *
	 * @return the history id
	 */
	HistoryId getHistoryId();

	/**
	 * Gets the period.
	 *
	 * @return the period
	 */
	DatePeriod getPeriod();

	/**
	 * Gets the lst work hierarchy.
	 *
	 * @return the lst work hierarchy
	 */
	List<WorkPlaceHierarchy> getLstWorkHierarchy();
}
