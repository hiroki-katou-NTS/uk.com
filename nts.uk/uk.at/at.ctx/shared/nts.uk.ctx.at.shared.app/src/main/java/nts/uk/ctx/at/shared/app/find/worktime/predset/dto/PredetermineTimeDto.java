/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.predset.dto;

import lombok.Builder;

/**
 * The Class PredetermineTimeDto.
 */
@Builder
public class PredetermineTimeDto {
	
	/** The add time. */
	public BreakDownTimeDayDto addTime;
	
	/** The pred time. */
	public BreakDownTimeDayDto predTime;
}
