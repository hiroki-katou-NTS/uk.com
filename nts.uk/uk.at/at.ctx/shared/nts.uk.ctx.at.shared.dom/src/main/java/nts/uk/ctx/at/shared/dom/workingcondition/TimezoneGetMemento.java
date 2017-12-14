/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workingcondition;

import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Interface TimezoneGetMemento.
 */
public interface TimezoneGetMemento {

	/**
	 * Gets the use atr.
	 *
	 * @return the use atr
	 */
	NotUseAtr getUseAtr();

	/**
	 * Gets the work no.
	 *
	 * @return the work no
	 */
	int getCnt();

	/**
	 * Gets the start.
	 *
	 * @return the start
	 */
	TimeWithDayAttr getStart();

	/**
	 * Gets the end.
	 *
	 * @return the end
	 */
	TimeWithDayAttr getEnd();
}
