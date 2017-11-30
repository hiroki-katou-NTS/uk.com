/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.estcomparison;

import nts.uk.ctx.at.schedule.dom.shift.estimate.EstComparisonAtr;

/**
 * The Interface EstimateComparisonGetMemento.
 */
public interface EstimateComparisonGetMemento {

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	String getCompanyId();
	
	/**
	 * Gets the comparison atr.
	 *
	 * @return the comparison atr
	 */
	EstComparisonAtr getComparisonAtr();
}
