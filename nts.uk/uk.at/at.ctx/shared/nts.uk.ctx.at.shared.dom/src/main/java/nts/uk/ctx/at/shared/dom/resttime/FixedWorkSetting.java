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
 * The Class FixedWorkSetting.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class FixedWorkSetting {

	/** The holiday attendance work time zone. */
	private HolidayAttendanceWorkTimeZone holidayAttendanceWorkTimeZone;
	
	/** The Half day attendance work time zone. */
	private HalfDayAttendanceWorkTimeZone halfDayAttendanceWorkTimeZone;
	
	/**
	 * Instantiates a new fixed work setting.
	 *
	 * @param memento the memento
	 */
	public FixedWorkSetting(FixedWorkSettingGetMemento memento){
		this.holidayAttendanceWorkTimeZone = memento.getHolidayAttendanceWorkTimeZone();
		this.halfDayAttendanceWorkTimeZone = memento.getHalfDayAttendanceWorkTimeZone();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(FixedWorkSettingSetMemento memento){
		memento.setHolidayAttendanceWorkTimeZone(this.holidayAttendanceWorkTimeZone);
		memento.setHalfDayAttendanceWorkTimeZone(this.halfDayAttendanceWorkTimeZone);
	}
	
}
