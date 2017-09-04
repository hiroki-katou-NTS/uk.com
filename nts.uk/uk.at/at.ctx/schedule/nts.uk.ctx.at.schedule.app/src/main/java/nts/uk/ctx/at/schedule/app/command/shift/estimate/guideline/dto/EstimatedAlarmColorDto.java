/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.estimate.guideline.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The Class EstimatedAlarmColorDto.
 */
@Data
@AllArgsConstructor
public class EstimatedAlarmColorDto {

	/** The guideline condition. */
	private int guidelineCondition;

	/** The color. */
	private String color;

	
}
