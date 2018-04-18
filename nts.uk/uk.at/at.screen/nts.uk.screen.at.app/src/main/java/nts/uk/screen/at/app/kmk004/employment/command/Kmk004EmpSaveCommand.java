/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.screen.at.app.kmk004.employment.command;

import lombok.Data;
import nts.uk.ctx.at.record.app.command.workrecord.monthcal.employment.SaveEmpMonthCalSetCommand;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.employmentnew.SaveEmpStatWorkTimeSetCommand;

/**
 * The Class Kmk004EmpSaveCommand.
 */
@Data
public class Kmk004EmpSaveCommand {

	/** The save stat command. */
	private SaveEmpStatWorkTimeSetCommand saveStatCommand;

	/** The save month command. */
	private SaveEmpMonthCalSetCommand saveMonthCommand;
}
