/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.resttime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class HalfDayAttendanceWorkTimeZone.
 */

/**
 * Gets the break time zone.
 *
 * @return the break time zone
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class HalfDayAttendanceWorkTimeZone {

	/** The break time zone. */
	private BreakTimeZone breakTimeZone;
	
	/**
	 * Instantiates a new half day attendance work time zone.
	 *
	 * @param memento the memento
	 */
	public HalfDayAttendanceWorkTimeZone (AttendanceWorkTimeZoneGetMemento memento){
		this.breakTimeZone = memento.getBreakTimeZone();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(AttendanceWorkTimeZoneSetMemento memento){
		memento.setBreakTimeZone(this.breakTimeZone);
	}
}
