/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew;

import nts.uk.ctx.at.shared.dom.common.MonthlyEstimateTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.Monthly;

/**
 * The Interface MonthlyTimeNewSetMemento.
 */
public interface MonthlyTimeNewSetMemento {

	/**
	 * Sets the monthly.
	 *
	 * @param monthly the new monthly
	 */
	void setMonthly(Monthly monthly);

	/**
	 * Sets the monthly time.
	 *
	 * @param monthlyTime the new monthly time
	 */
	void setMonthlyTime(MonthlyEstimateTime monthlyTime);

}
