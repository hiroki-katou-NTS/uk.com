/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.pattern.monthly.setting;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class MonthlyPatternSettingDto.
 */
@Getter
@Setter
public class MonthlyPatternSettingDto {

	/** The employee id. */
	private String employeeId;
	
	/** The history id. */
	private String historyId;

	/** The monthly pattern code. */
	private String monthlyPatternCode;
}
