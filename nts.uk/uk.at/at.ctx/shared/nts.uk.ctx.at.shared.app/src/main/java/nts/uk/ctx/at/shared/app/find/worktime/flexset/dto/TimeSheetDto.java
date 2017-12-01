/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.flexset.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.flexset.TimeSheetSetMemento;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class TimeSheetDto.
 */
@Getter
@Setter
public class TimeSheetDto implements TimeSheetSetMemento{

	/** The start time. */
	private Integer startTime;

	/** The end time. */
	private Integer endTime;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.TimeSheetSetMemento#
	 * setStartTime(nts.uk.shr.com.time.TimeWithDayAttr)
	 */
	@Override
	public void setStartTime(TimeWithDayAttr startTime) {
		this.startTime = startTime.valueAsMinutes();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flexset.TimeSheetSetMemento#setEndTime(
	 * nts.uk.shr.com.time.TimeWithDayAttr)
	 */
	@Override
	public void setEndTime(TimeWithDayAttr endTime) {
		this.endTime = endTime.valueAsMinutes();
	}
	
	
}
