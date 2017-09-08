/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.shift.estimate.commonset.dto;

import lombok.Data;

/**
 * The Class ReferenceConditionDto.
 */
@Data
public class ReferenceConditionDto {

	/** The yearly display condition. */
	private int yearlyDisplayCondition;

	/** The monthly display condition. */
	private int monthlyDisplayCondition;

	/** The alarm check condition. */
	private int alarmCheckCondition;

}
