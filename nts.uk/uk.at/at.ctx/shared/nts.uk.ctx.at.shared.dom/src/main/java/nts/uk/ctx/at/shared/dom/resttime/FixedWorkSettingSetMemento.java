/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.resttime;

/**
 * The Interface FixedWorkSettingSetMemento.
 */
public interface FixedWorkSettingSetMemento {

	/**
	 * Sets the holiday attendance work time zone.
	 *
	 * @param holidayAttendanceWorkTimeZone the new holiday attendance work time zone
	 */
	public void setHolidayAttendanceWorkTimeZone(HolidayAttendanceWorkTimeZone holidayAttendanceWorkTimeZone);

	/**
	 * Sets the half day attendance work time zone.
	 *
	 * @param HalfDayAttendanceWorkTimeZone the new half day attendance work time zone
	 */
	public void setHalfDayAttendanceWorkTimeZone(HalfDayAttendanceWorkTimeZone HalfDayAttendanceWorkTimeZone);
}
