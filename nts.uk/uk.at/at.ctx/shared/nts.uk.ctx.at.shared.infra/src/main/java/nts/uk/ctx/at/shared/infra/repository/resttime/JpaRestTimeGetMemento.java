/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.resttime;

import nts.uk.ctx.at.shared.dom.resttime.FixedWorkSettingGetMemento;
import nts.uk.ctx.at.shared.dom.resttime.HalfDayAttendanceWorkTimeZone;
import nts.uk.ctx.at.shared.dom.resttime.HolidayAttendanceWorkTimeZone;
import nts.uk.ctx.at.shared.infra.entity.resttime.KwtmtRestTime;

/**
 * The Class JpaRestTimeGetMemento.
 */
public class JpaRestTimeGetMemento implements FixedWorkSettingGetMemento {

	/** The entity. */
	private KwtmtRestTime entity;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.resttime.FixedWorkSettingGetMemento#
	 * getHolidayAttendanceWorkTimeZone()
	 */
	@Override
	public HolidayAttendanceWorkTimeZone getHolidayAttendanceWorkTimeZone() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.resttime.FixedWorkSettingGetMemento#
	 * getHalfDayAttendanceWorkTimeZone()
	 */
	@Override
	public HalfDayAttendanceWorkTimeZone getHalfDayAttendanceWorkTimeZone() {
		return null;
	}

}
