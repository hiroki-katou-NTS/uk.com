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
 * The Class HolidayAttendanceWorkTimeZone.
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class HolidayAttendanceWorkTimeZone {

	/** The break time zone. */
	private BreakTimeZone breakTimeZone;
	
	/**
	 * Instantiates a new holiday attendance work time zone.
	 *
	 * @param memento the memento
	 */
	public HolidayAttendanceWorkTimeZone (AttendanceWorkTimeZoneGetMemento memento){
		this.breakTimeZone = memento.getBreakTimeZone();
	}
	
	/**
	 * Save to memento.
	 *
	 * @p`aram memento the memento
	 */
	public void saveToMemento(AttendanceWorkTimeZoneSetMemento memento){
		memento.setBreakTimeZone(this.breakTimeZone);
	}
}
