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
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	void setCompanyId(CompanyId companyId);
	
	/**
	 * Sets the closure history id.
	 *
	 * @param closureHistoryId the new closure history id
	 */
	void setClosureHistoryId(ClosureHistoryId closureHistoryId);

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
