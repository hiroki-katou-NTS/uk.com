/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.closure;

import nts.arc.time.YearMonth;

/**
 * The Interface ClosureHistorySetMemento.
 */
public interface ClosureHistorySetMemento {

	/**
	 * Sets the close name.
	 *
	 * @param closeName the new close name
	 */
	void setCloseName(CloseName closeName);

	/**
	 * Sets the closure id.
	 *
	 * @param closureId the new closure id
	 */
	void setClosureId(ClosureId closureId);

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
