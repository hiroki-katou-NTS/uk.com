/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.shift.pattern.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WorkMonthlySettingFindDto {

	/** The monthly pattern code. */
	private String monthlyPatternCode;
	
	/** The year month. */
	private int yearMonth;
}
