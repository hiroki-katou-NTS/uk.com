/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.shortworktime;

import java.util.List;

/**
 * The Interface SWorkTimeHistItemSetMemento.
 */
public interface SWorkTimeHistItemSetMemento {

	/**
	 * Sets the employee id.
	 *
	 * @param mployeeId the new employee id
	 */
	void setEmployeeId(String employeeId);
	
	/**
	 * Sets the history id.
	 *
	 * @param historyId the new history id
	 */
	void setHistoryId(String historyId);

	/**
	 * Sets the child care atr.
	 *
	 * @param childCareAtr the new child care atr
	 */
	void setChildCareAtr(ChildCareAtr childCareAtr);

	/**
	 * Sets the lst time zone.
	 *
	 * @param lstTimeSlot the new lst time zone
	 */
	void setLstTimeSlot(List<SChildCareFrame> lstTimeSlot);
}
