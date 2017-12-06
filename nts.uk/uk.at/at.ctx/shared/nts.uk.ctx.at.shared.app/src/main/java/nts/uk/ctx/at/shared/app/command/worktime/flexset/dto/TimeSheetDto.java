/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.flexset.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.flexset.TimeSheetGetMemento;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class TimeSheetDto.
 */
@Getter

/**
 * Sets the end time.
 *
 * @param endTime the new end time
 */
@Setter
public class TimeSheetDto implements TimeSheetGetMemento{
	
	/** The start time. */
	private Integer startTime;

	/** The end time. */
	private Integer endTime;


	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.TimeSheetGetMemento#getStartTime()
	 */
	@Override
	public TimeWithDayAttr getStartTime() {
		return new TimeWithDayAttr(this.startTime);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.TimeSheetGetMemento#getEndTime()
	 */
	@Override
	public TimeWithDayAttr getEndTime() {
		return new TimeWithDayAttr(this.endTime);
	}

}
