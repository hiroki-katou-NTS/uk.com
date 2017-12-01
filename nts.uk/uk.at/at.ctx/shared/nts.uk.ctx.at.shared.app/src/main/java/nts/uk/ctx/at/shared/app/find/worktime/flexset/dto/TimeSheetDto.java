/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.flexset.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class TimeSheetDto.
 */
@Getter
@Setter
public class TimeSheetDto {

	/** The start time. */
	private Integer startTime;

	/** The end time. */
	private Integer endTime;

	/**
	 * Instantiates a new time sheet dto.
	 *
	 * @param startTime the start time
	 * @param endTime the end time
	 */
	public TimeSheetDto(Integer startTime, Integer endTime) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	
}
