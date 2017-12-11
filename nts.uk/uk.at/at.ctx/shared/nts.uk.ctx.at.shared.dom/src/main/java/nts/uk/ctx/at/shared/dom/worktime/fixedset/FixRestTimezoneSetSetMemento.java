/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fixedset;

import java.util.List;

import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;

/**
 * The Interface FixRestTimezoneSetSetMemento.
 */
public interface FixRestTimezoneSetSetMemento {

	/**
	 * Sets the lst timezone.
	 *
	 * @param lstTimezone the new lst timezone
	 */
	void setLstTimezone(List<DeductionTime> lstTimezone);
	
}
