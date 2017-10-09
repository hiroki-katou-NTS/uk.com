/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.executionlog.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class ScheduleExecutionLogInfoDto.
 */
@Getter
@Setter
public class ScheduleExecutionLogInfoDto {
	
	/** The total number employee. */
	private int totalNumber;
	
	
	/** The total number employee created. */
	private int totalNumberCreated;
	
	
	/** The total number error. */
	private int totalNumberError;

}
