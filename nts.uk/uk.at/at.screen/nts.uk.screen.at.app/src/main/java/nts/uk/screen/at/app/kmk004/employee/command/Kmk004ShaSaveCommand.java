/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.screen.at.app.kmk004.employee.command;

import lombok.Data;
import nts.uk.ctx.at.record.app.command.workrecord.monthcal.employee.SaveShaMonthCalSetCommand;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.employeenew.SaveShainStatWorkTimeSetCommand;

/**
 * The Class Kmk004ShaSaveCommand.
 */
@Data
public class Kmk004ShaSaveCommand {

	/** The save stat command. */
	private SaveShainStatWorkTimeSetCommand saveStatCommand;

	/** The save month command. */
	private SaveShaMonthCalSetCommand saveMonthCommand;
}
