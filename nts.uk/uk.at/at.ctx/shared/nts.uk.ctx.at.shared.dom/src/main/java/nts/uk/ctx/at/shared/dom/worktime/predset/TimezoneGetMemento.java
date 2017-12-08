/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.predset;

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
	UseSetting getUseAtr();
	
	
	
	/**
	 * Gets the work no.
	 *
	 * @return the work no
	 */
	int getWorkNo();
	
	
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
