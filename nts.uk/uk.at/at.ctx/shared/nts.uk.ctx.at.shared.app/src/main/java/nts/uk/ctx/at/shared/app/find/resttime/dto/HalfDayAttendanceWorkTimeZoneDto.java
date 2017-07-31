/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.resttime.dto;

import nts.uk.ctx.at.shared.dom.resttime.AttendanceWorkTimeZoneSetMemento;
import nts.uk.ctx.at.shared.dom.resttime.BreakTimeZone;

/**
 * The Class HalfDayAttendanceWorkTimeZoneDto.
 */
public class HalfDayAttendanceWorkTimeZoneDto implements AttendanceWorkTimeZoneSetMemento{

	/** The break time zone dto. */
	public BreakTimeZoneDto breakTimeZone;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.resttime.AttendanceWorkTimeZoneSetMemento#setBreakTimeZone(nts.uk.ctx.at.shared.dom.resttime.BreakTimeZone)
	 */
	@Override
	public void setBreakTimeZone(BreakTimeZone breakTimeZone) {
		BreakTimeZoneDto btz = new BreakTimeZoneDto();
		breakTimeZone.saveToMemento(btz);
		this.breakTimeZone = btz;
	}
}
