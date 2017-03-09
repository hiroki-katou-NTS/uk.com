/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.history.service;

import nts.uk.ctx.pr.core.dom.wagetable.WageTableHead;

/**
 * The Class WageTableHeadService.
 */
public abstract class WageTableHeadService {

	/**
	 * Validate required item.
	 *
	 * @param head the head
	 */
	public abstract void validateRequiredItem(WageTableHead head);

	/**
	 * Check duplicate code.
	 *
	 * @param head the head
	 */
	public abstract void checkDuplicateCode(WageTableHead head);
}
