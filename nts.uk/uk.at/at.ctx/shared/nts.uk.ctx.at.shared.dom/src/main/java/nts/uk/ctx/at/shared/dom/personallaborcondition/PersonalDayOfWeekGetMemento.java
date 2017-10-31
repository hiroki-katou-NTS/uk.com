/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.personallaborcondition;

import java.util.Optional;

/**
 * The Interface PersonalDayOfWeekSetMemento.
 */
public interface PersonalDayOfWeekGetMemento {
	
	/**
	 * Gets the saturday.
	 *
	 * @return the saturday
	 */
	public Optional<SingleDaySchedule> getSaturday();
	
	
	/**
	 * Gets the sunday.
	 *
	 * @return the sunday
	 */
	public Optional<SingleDaySchedule> getSunday();
	
	
	/**
	 * Gets the monday.
	 *
	 * @return the monday
	 */
	public Optional<SingleDaySchedule> getMonday();

	
	
	/**
	 * Gets the thursday.
	 *
	 * @return the thursday
	 */
	public Optional<SingleDaySchedule> getThursday();
	
	
	/**
	 * Gets the wednesday.
	 *
	 * @return the wednesday
	 */
	public Optional<SingleDaySchedule> getWednesday();
	
	
	/**
	 * Gets the tuesday.
	 *
	 * @return the tuesday
	 */
	public Optional<SingleDaySchedule> getTuesday();
	
	
	/**
	 * Gets the friday.
	 *
	 * @return the friday
	 */
	public Optional<SingleDaySchedule> getFriday();
}
