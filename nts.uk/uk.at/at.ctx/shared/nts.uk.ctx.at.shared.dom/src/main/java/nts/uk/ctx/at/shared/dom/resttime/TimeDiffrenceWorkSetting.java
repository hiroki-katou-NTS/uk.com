/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.resttime;

import lombok.Getter;

/**
 * The Class TimeDiffrenceWorkSetting.
 */
@Getter
public class TimeDiffrenceWorkSetting {

	/** The holiday attendance work time zone. */
	private HolidayAttendanceWorkTimeZone holidayAttendanceWorkTimeZone;
	
	/** The Half day attendance work time zone. */
	private HalfDayAttendanceWorkTimeZone HalfDayAttendanceWorkTimeZone;
}
