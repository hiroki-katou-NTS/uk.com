/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.schedule.basicschedule.childcareschedule;

import nts.uk.ctx.at.shared.dom.shortworktime.ChildCareAtr;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Interface ChildCareScheduleGetMemento.
 */
public interface ChildCareScheduleGetMemento {

	/**
	 * Gets the child care number.
	 *
	 * @return the child care number
	 */
	public ChildCareScheduleRound getChildCareNumber();
	
	
	/**
	 * Gets the child care schedule start.
	 *
	 * @return the child care schedule start
	 */
	public TimeWithDayAttr getChildCareScheduleStart();
	
	
	/**
	 * Gets the child care schedule end.
	 *
	 * @return the child care schedule end
	 */
	public TimeWithDayAttr getChildCareScheduleEnd();
	
	
	/**
	 * Gets the child care atr.
	 *
	 * @return the child care atr
	 */
	public ChildCareAtr getChildCareAtr();
}
