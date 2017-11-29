/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.schedule.basicschedule.workscheduletimezone;

import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletimezone.BounceAtr;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletimezone.WorkScheduleTimeZoneGetMemento;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.workscheduletimezone.KscdtWorkScheduleTimeZone;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class JpaWorkScheduleTimeZoneGetMemento.
 */
public class JpaWorkScheduleTimeZoneGetMemento implements WorkScheduleTimeZoneGetMemento {
	
	/** The entity. */
	private KscdtWorkScheduleTimeZone entity;
	
	/**
	 * Instantiates a new jpa work schedule time zone get memento.
	 *
	 * @param entity the entity
	 */
	public JpaWorkScheduleTimeZoneGetMemento(KscdtWorkScheduleTimeZone entity) {
		this.entity = entity;
	}

	/**
	 * Gets the schedule cnt.
	 *
	 * @return the schedule cnt
	 */
	@Override
	public int getScheduleCnt() {
		return this.entity.kscdtWorkScheduleTimeZonePk.scheduleCnt;
	}

	/**
	 * Gets the schedule start clock.
	 *
	 * @return the schedule start clock
	 */
	@Override
	public TimeWithDayAttr getScheduleStartClock() {
		return new TimeWithDayAttr(this.entity.scheduleStartClock);
	}

	/**
	 * Gets the bounce atr.
	 *
	 * @return the bounce atr
	 */
	@Override
	public BounceAtr getBounceAtr() {
		return BounceAtr.valueOf(this.entity.bounceAtr);
	}

	/**
	 * Gets the schedule end clock.
	 *
	 * @return the schedule end clock
	 */
	@Override
	public TimeWithDayAttr getScheduleEndClock() {
		return new TimeWithDayAttr(this.entity.scheduleEndClock);
	}
	

}
