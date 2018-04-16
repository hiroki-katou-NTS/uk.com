/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.screen.at.app.kmk004.workplace.command;

import lombok.Data;
import nts.uk.ctx.at.record.app.command.workrecord.monthcal.workplace.SaveWkpMonthCalSetCommand;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.workplacenew.SaveWkpStatWorkTimeSetCommand;

/**
 * The Class Kmk004WkpSaveCommand.
 */
@Data
public class Kmk004WkpSaveCommand {

	/** The save stat command. */
	private SaveWkpStatWorkTimeSetCommand saveStatCommand;

	/** The save month command. */
	private SaveWkpMonthCalSetCommand saveMonthCommand;
}
