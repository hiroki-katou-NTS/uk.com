/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.schedule.basicschedule;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workschedulebreak.ScheduledBreakCnt;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workschedulebreak.WorkScheduleBreakGetMemento;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class WorkScheduleBreakSaveCommand.
 */
@Getter
@Setter
public class WorkScheduleBreakSaveCommand implements WorkScheduleBreakGetMemento{
	
	/** The schedule break cnt. */
	private int scheduleBreakCnt;
	
	/** The scheduled start clock. */
	private int  scheduledStartClock;
	
	/** The scheduled end clock. */
	private int scheduledEndClock;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workschedulebreak.
	 * WorkScheduleBreakGetMemento#getScheduleBreakCnt()
	 */
	@Override
	public ScheduledBreakCnt getScheduleBreakCnt() {
		return new ScheduledBreakCnt(scheduleBreakCnt);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workschedulebreak.
	 * WorkScheduleBreakGetMemento#getScheduledStartClock()
	 */
	@Override
	public TimeWithDayAttr getScheduledStartClock() {
		return new TimeWithDayAttr(scheduledStartClock);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workschedulebreak.
	 * WorkScheduleBreakGetMemento#getScheduledEndClock()
	 */
	@Override
	public TimeWithDayAttr getScheduledEndClock() {
		return new TimeWithDayAttr(scheduledEndClock);
	}

}
