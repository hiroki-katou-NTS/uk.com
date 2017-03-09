/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.history.service;

import nts.uk.ctx.pr.core.dom.base.simplehistory.SimpleHistoryBaseService;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableHead;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistory;

/**
 * The Class WageTableHistoryService.
 */
public abstract class WageTableHistoryService
		extends SimpleHistoryBaseService<WageTableHead, WageTableHistory> {

	/**
	 * Validate required item.
	 *
	 * @param unitPriceHistory the unit price history
	 */
	public abstract void validateRequiredItem(WageTableHistory unitPriceHistory);

	/**
	 * Validate date range.
	 *
	 * @param unitPriceHistory the unit price history
	 */
	public abstract void validateDateRange(WageTableHistory unitPriceHistory);

}
