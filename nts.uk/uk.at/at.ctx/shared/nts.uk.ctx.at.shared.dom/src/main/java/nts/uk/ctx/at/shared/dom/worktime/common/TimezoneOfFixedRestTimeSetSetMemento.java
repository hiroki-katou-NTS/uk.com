/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import java.util.List;

import nts.uk.ctx.at.shared.dom.worktime_old.DeductionTime;

/**
 * The Interface TimezoneOfFixedRestTimeSetSetMemento.
 */
public interface TimezoneOfFixedRestTimeSetSetMemento {

	/**
	 * Sets the timezone.
	 *
	 * @param list the new timezone
	 */
	void setTimezone(List<DeductionTime> list);
}
