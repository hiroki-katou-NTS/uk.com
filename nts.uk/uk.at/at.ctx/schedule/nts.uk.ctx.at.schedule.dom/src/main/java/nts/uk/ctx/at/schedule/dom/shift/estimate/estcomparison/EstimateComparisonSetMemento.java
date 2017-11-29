/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.estcomparison;

import nts.uk.ctx.at.schedule.dom.shift.estimate.EstComparisonAtr;

/**
 * The Interface EstimateComparisonSetMemento.
 */
public interface EstimateComparisonSetMemento {

	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	void setCompanyId(String companyId);
	
	/**
	 * Sets the comparison atr.
	 *
	 * @param comparisonAtr the new comparison atr
	 */
	void setComparisonAtr(EstComparisonAtr comparisonAtr);
}
