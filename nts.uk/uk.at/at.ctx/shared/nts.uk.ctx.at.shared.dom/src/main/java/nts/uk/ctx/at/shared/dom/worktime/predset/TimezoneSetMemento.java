/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.predset;

import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Interface TimezoneSetMemento.
 */
public interface TimezoneSetMemento {

	/**
	 * Sets the use atr.
	 *
	 * @param useAtr the new use atr
	 */
	void setUseAtr(UseSetting useAtr);
	
	
	/**
	 * Sets the work no.
	 *
	 * @param workNo the new work no
	 */
	void setWorkNo(int workNo);
	
	
	/**
	 * Sets the start.
	 *
	 * @param start the new start
	 */
	void setStart(TimeWithDayAttr start);
	
	/**
	 * Sets the end.
	 *
	 * @param end the new end
	 */
	void setEnd(TimeWithDayAttr end);
}
