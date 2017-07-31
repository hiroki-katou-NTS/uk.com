/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.resttime;

/**
 * The Interface FixedWorkSettingGetMemento.
 */
public interface FixedWorkSettingGetMemento {

	/**
	 * Gets the holiday attendance work time zone.
	 *
	 * @return the holiday attendance work time zone
	 */
	public HolidayAttendanceWorkTimeZone getHolidayAttendanceWorkTimeZone();

    /**
     * Gets the half day attendance work time zone.
     *
     * @return the half day attendance work time zone
     */
    public HalfDayAttendanceWorkTimeZone getHalfDayAttendanceWorkTimeZone();
}
