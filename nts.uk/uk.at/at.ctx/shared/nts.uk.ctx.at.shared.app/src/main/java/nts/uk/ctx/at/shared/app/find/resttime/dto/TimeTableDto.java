/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.resttime.dto;

import javax.ejb.Stateless;

import nts.uk.ctx.at.shared.dom.resttime.TimeTableSetMemento;
import nts.uk.ctx.at.shared.dom.worktimeset.TimeDayAtr;

/**
 * The Class TimeTableDto.
 */
@Stateless
public class TimeTableDto implements TimeTableSetMemento {

	/** The start day. */
	public Integer startDay;

	/** The start time. */
	public Integer startTime;

	/** The end day. */
	public Integer endDay;

	/** The end time. */
	public Integer endTime;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.resttime.TimeTableSetMemento#setStartDay(nts.uk.
	 * ctx.at.shared.dom.worktimeset.TimeDayAtr)
	 */
	@Override
	public void setStartDay(TimeDayAtr startDay) {
		this.startDay = startDay.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.resttime.TimeTableSetMemento#setStartTime(java.
	 * lang.Integer)
	 */
	@Override
	public void setStartTime(Integer startTime) {
		this.startTime = startTime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.resttime.TimeTableSetMemento#setEndDay(nts.uk.
	 * ctx.at.shared.dom.worktimeset.TimeDayAtr)
	 */
	@Override
	public void setEndDay(TimeDayAtr endDay) {
		this.endDay = endDay.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.resttime.TimeTableSetMemento#setEndTime(java.
	 * lang.Integer)
	 */
	@Override
	public void setEndTime(Integer endTime) {
		this.endTime = endTime;
	}
}
