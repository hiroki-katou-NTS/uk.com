/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.schedule.basicschedule.childcareschedule;

import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.ClockValue;

/**
 * The Interface ChildCareScheduleGetMemento.
 */
public interface ChildCareScheduleSetMemento {

	/**
	 * Sets the child care number.
	 *
	 * @param childCareNumber the new child care number
	 */
	public void setChildCareNumber(ChildCareScheduleRound childCareNumber);
	
	
	/**
	 * Gets the child care schedule start.
	 *
	 * @return the child care schedule start
	 */
	public void setChildCareScheduleStart(ClockValue childCareScheduleStart);
	
	
	/**
	 * Gets the child care schedule end.
	 *
	 * @return the child care schedule end
	 */
	public void setChildCareScheduleEnd(ClockValue childCareScheduleEnd);
	
	
	/**
	 * Gets the child care atr.
	 *
	 * @return the child care atr
	 */
	public void setChildCareAtr(ChildCareAtr childCareAtr);
}
