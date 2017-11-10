/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.shortworktime;

import java.util.List;

/**
 * The Interface SWorkTimeHistItemGetMemento.
 */
public interface SWorkTimeHistItemGetMemento {

	/**
	 * Gets the history id.
	 *
	 * @return the history id
	 */
	String getHistoryId();
	
	/**
	 * Gets the employee id.
	 *
	 * @return the employee id
	 */
	String getEmployeeId();
	
	/**
	 * Gets the child care atr.
	 *
	 * @return the child care atr
	 */
	ChildCareAtr getChildCareAtr();
	
	/**
	 * Gets the lst time slot.
	 *
	 * @return the lst time slot
	 */
	List<SChildCareFrame> getLstTimeSlot();
}
