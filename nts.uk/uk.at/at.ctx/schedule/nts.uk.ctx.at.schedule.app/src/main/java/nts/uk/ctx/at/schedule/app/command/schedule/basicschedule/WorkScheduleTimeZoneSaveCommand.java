/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.schedule.basicschedule;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletimezone.BounceAtr;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletimezone.WorkScheduleTimeZoneGetMemento;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class WorkScheduleTimeZoneSaveCommand.
 */
@Getter
@Setter
public class WorkScheduleTimeZoneSaveCommand implements WorkScheduleTimeZoneGetMemento {

	/** The schedule cnt. */
	private int scheduleCnt;

	/** The schedule start clock. */
	private Integer scheduleStartClock;

	/** The schedule end clock. */
	private Integer scheduleEndClock;

	/** The bounce atr. */
	private int bounceAtr;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletimezone.
	 * WorkScheduleTimeZoneGetMemento#getScheduleCnt()
	 */
	@Override
	public int getScheduleCnt() {
		return scheduleCnt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletimezone.
	 * WorkScheduleTimeZoneGetMemento#getScheduleStartClock()
	 */
	@Override
	public TimeWithDayAttr getScheduleStartClock() {
		return scheduleStartClock != null ? new TimeWithDayAttr(scheduleStartClock) : null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletimezone.
	 * WorkScheduleTimeZoneGetMemento#getBounceAtr()
	 */
	@Override
	public BounceAtr getBounceAtr() {
		return BounceAtr.valueOf(bounceAtr);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletimezone.
	 * WorkScheduleTimeZoneGetMemento#getScheduleEndClock()
	 */
	@Override
	public TimeWithDayAttr getScheduleEndClock() {
		return scheduleEndClock != null ? new TimeWithDayAttr(scheduleEndClock) : null;
	}

}
