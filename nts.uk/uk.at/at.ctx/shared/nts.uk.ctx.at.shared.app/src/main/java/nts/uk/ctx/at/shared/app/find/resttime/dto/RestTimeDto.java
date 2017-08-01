/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.resttime.dto;

import nts.uk.ctx.at.shared.dom.resttime.FixedWorkSettingSetMemento;
import nts.uk.ctx.at.shared.dom.resttime.HalfDayAttendanceWorkTimeZone;
import nts.uk.ctx.at.shared.dom.resttime.HolidayAttendanceWorkTimeZone;

/**
 * The Class RestTimeDto.
 */
public class RestTimeDto implements FixedWorkSettingSetMemento {

	/** The holiday attendance work time zone dto. */
	public HolidayAttendanceWorkTimeZoneDto holidayAttendanceWorkTimeZone;

	/** The half day attendance work time zone dto. */
	public HalfDayAttendanceWorkTimeZoneDto halfDayAttendanceWorkTimeZone;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.resttime.FixedWorkSettingSetMemento#setHolidayAttendanceWorkTimeZone(nts.uk.ctx.at.shared.dom.resttime.HolidayAttendanceWorkTimeZone)
	 */
	@Override
	public void setHolidayAttendanceWorkTimeZone(HolidayAttendanceWorkTimeZone holidayAttendanceWorkTimeZone) {
		HolidayAttendanceWorkTimeZoneDto holiday = new HolidayAttendanceWorkTimeZoneDto();
		holidayAttendanceWorkTimeZone.saveToMemento(holiday);
		this.holidayAttendanceWorkTimeZone = holiday;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.resttime.FixedWorkSettingSetMemento#setHalfDayAttendanceWorkTimeZone(nts.uk.ctx.at.shared.dom.resttime.HalfDayAttendanceWorkTimeZone)
	 */
	@Override
	public void setHalfDayAttendanceWorkTimeZone(HalfDayAttendanceWorkTimeZone halfDayAttendanceWorkTimeZone) {
		HalfDayAttendanceWorkTimeZoneDto halfDay = new HalfDayAttendanceWorkTimeZoneDto();
		halfDayAttendanceWorkTimeZone.saveToMemento(halfDay);
		this.halfDayAttendanceWorkTimeZone = halfDay;
	}
}
