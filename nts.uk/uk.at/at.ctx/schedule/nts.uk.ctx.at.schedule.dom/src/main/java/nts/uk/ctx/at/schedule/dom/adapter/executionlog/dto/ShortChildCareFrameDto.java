/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.adapter.executionlog.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class ShortChildCareFrameDto.
 */
@Getter
@Setter
public class ShortChildCareFrameDto {
	
	/** The time slot. */
	private int timeSlot;
	
	/** The start time. */
	private TimeWithDayAttr startTime;
	
	/** The end time. */
	private TimeWithDayAttr endTime;
}
