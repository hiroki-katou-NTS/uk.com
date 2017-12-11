/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workingcondition;

import java.util.Optional;

/**
 * The Interface PersonalDayOfWeekSetMemento.
 */
public interface PersonalDayOfWeekSetMemento {
	
	/**
	 * Sets the saturday.
	 *
	 * @param saturday the new saturday
	 */
	public void setSaturday(Optional<SingleDaySchedule> saturday);
	
	
	/**
	 * Sets the sunday.
	 *
	 * @param sunday the new sunday
	 */
	public void setSunday(Optional<SingleDaySchedule> sunday);
	
	
	/**
	 * Sets the monday.
	 *
	 * @param monday the new monday
	 */
	public void setMonday(Optional<SingleDaySchedule> monday);
	
	
	/**
	 * Sets the thursday.
	 *
	 * @param thursday the new thursday
	 */
	public void setThursday(Optional<SingleDaySchedule> thursday);
	
	
	/**
	 * Sets the wednesday.
	 *
	 * @param wednesday the new wednesday
	 */
	public void setWednesday(Optional<SingleDaySchedule> wednesday);
	
	
	/**
	 * Sets the tuesday.
	 *
	 * @param tuesday the new tuesday
	 */
	public void setTuesday(Optional<SingleDaySchedule> tuesday);
	
	
	/**
	 * Sets the friday.
	 *
	 * @param friday the new friday
	 */
	public void setFriday(Optional<SingleDaySchedule> friday);

}
