/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workrule.closure;

import nts.arc.time.YearMonth;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 * The Interface ClosureHistoryGetMemento.
 */
public interface ClosureHistoryGetMemento {

	/**
	 * Gets the closure name.
	 *
	 * @return the closure name
	 */
	ClosureName getClosureName();

	/**
	 * Gets the closure id.
	 *
	 * @return the closure id
	 */
	ClosureId getClosureId();

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	CompanyId getCompanyId();

	/**
	 * Gets the end date.
	 *
	 * @return the end date
	 */
	YearMonth getEndDate();

	/**
	 * Gets the closure date.
	 *
	 * @return the closure date
	 */
	ClosureDate getClosureDate();

	/**
	 * Gets the start date.
	 *
	 * @return the start date
	 */
	YearMonth getStartDate();
}