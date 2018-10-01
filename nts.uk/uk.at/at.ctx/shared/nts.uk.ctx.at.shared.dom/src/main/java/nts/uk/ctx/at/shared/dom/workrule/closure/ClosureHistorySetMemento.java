/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workrule.closure;

import nts.arc.time.YearMonth;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 * The Interface ClosureHistorySetMemento.
 */
public interface ClosureHistorySetMemento {

	/**
	 * Sets the closure name.
	 *
	 * @param closureName the new closure name
	 */
	void setClosureName(ClosureName closureName);

	/**
	 * Sets the closure id.
	 *
	 * @param closureId the new closure id
	 */
	void setClosureId(ClosureId closureId);
	
	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	void setCompanyId(CompanyId companyId);
	
	/**
	 * Sets the end date.
	 *
	 * @param endDate the new end date
	 */
	void setEndDate(YearMonth endDate);

	/**
	 * Sets the closure date.
	 *
	 * @param closureDate the new closure date
	 */
	void setClosureDate(ClosureDate closureDate);
	
	/**
	 * Sets the start date.
	 *
	 * @param startDate the new start date
	 */
	void setStartDate(YearMonth startDate);
}
