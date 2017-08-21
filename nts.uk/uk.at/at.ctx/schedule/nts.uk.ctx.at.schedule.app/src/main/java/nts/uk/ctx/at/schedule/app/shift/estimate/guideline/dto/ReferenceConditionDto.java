/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.shift.estimate.guideline.dto;

import lombok.Data;

/**
 * The Class ReferenceConditionDto.
 */
@Data
public class ReferenceConditionDto {

	/** The yearly display condition. */
	private Integer yearlyDisplayCondition;

	/** The monthly display condition. */
	private Integer monthlyDisplayCondition;

	/** The alarm check condition. */
	private Integer alarmCheckCondition;

}
