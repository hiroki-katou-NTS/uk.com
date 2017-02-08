/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.rule.employment.unitprice.service;

import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceHistory;

/**
 * The Interface UnitPriceHistoryService.
 */
public interface UnitPriceHistoryService {

	/**
	 * Validate required item.
	 *
	 * @param unitPriceHistory the unit price history
	 */
	void validateRequiredItem(UnitPriceHistory unitPriceHistory);

	/**
	 * Validate date range.
	 *
	 * @param unitPriceHistory the unit price history
	 */
	void validateDateRange(UnitPriceHistory unitPriceHistory);
}
