/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.resttime;

import nts.uk.ctx.at.shared.dom.resttime.FixedWorkSettingSetMemento;
import nts.uk.ctx.at.shared.dom.resttime.HalfDayAttendanceWorkTimeZone;
import nts.uk.ctx.at.shared.dom.resttime.HolidayAttendanceWorkTimeZone;
import nts.uk.ctx.at.shared.infra.entity.resttime.KwtmtRestTime;

/**
 * The Class JpaRestTimeSetMemento.
 */
public class JpaRestTimeSetMemento implements FixedWorkSettingSetMemento {

	/** The entity. */
	private KwtmtRestTime entity;
	
	
	/**
	 * Instantiates a new jpa rest time set memento.
	 *
	 * @param entity the entity
	 */
	public JpaRestTimeSetMemento(KwtmtRestTime entity) {
		this.entity = entity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.resttime.FixedWorkSettingSetMemento#setHolidayAttendanceWorkTimeZone(nts.uk.ctx.at.shared.dom.resttime.HolidayAttendanceWorkTimeZone)
	 */
	@Override
	public void setHolidayAttendanceWorkTimeZone(HolidayAttendanceWorkTimeZone holidayAttendanceWorkTimeZone) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.resttime.FixedWorkSettingSetMemento#setHalfDayAttendanceWorkTimeZone(nts.uk.ctx.at.shared.dom.resttime.HalfDayAttendanceWorkTimeZone)
	 */
	@Override
	public void setHalfDayAttendanceWorkTimeZone(HalfDayAttendanceWorkTimeZone HalfDayAttendanceWorkTimeZone) {
		// TODO Auto-generated method stub
		
	}

}
