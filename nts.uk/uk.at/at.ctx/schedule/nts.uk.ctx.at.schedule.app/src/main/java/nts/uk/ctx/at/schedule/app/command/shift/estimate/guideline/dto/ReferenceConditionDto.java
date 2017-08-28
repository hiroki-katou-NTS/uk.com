/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.estimate.guideline.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The Class ReferenceConditionDto.
 */
@Data
@AllArgsConstructor
public class ReferenceConditionDto {

	/** The yearly display condition. */
	private int yearlyDisplayCondition;

	/** The monthly display condition. */
	private int monthlyDisplayCondition;

	/** The alarm check condition. */
	private int alarmCheckCondition;

}
