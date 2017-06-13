/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.closure;

import nts.arc.time.YearMonth;

/**
 * The Class ClosureHistoryGetMemento.
 */
public interface ClosureHistoryGetMemento {
	
	/**
	 * Gets the close name.
	 *
	 * @return the close name
	 */
	CloseName getCloseName();

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
	 * Gets the closure history id.
	 *
	 * @return the closure history id
	 */
	ClosureHistoryId getClosureHistoryId();
	
	
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
