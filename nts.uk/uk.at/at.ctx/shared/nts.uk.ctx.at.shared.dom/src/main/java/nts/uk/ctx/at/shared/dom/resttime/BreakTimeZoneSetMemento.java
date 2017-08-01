/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.resttime;

import java.util.List;

/**
 * The Interface BreakTimeZoneSetMemento.
 */
public interface BreakTimeZoneSetMemento {

	/**
  	 * Sets the time table.
  	 *
  	 * @param timeTable the new time table
  	 */
  	public void setTimeTable(List<TimeTable> timeTable);
}
