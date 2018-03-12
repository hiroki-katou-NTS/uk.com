/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.pattern.monthly.setting;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class MonthlyPatternSettingSaveCommand.
 */
@Getter
@Setter
public class MonthlyPatternSettingDeleteCommand {

	/** The employee id. */
	private String employeeId;

	/** The monthly pattern code. */
	private String monthlyPatternCode;
	
	/** The history id. */
	private String historyId;
}
