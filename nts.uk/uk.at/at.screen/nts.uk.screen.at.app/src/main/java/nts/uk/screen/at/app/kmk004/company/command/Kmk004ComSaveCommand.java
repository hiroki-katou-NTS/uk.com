/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.screen.at.app.kmk004.company.command;

import lombok.Data;
import nts.uk.ctx.at.record.app.command.workrecord.monthcal.company.SaveComMonthCalSetCommand;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.companyNew.SaveComStatWorkTimeSetCommand;

/**
 * The Class Kmk004ComAddCommand.
 */
@Data
public class Kmk004ComSaveCommand {

	/** The save com stat work time set command. */
	private SaveComStatWorkTimeSetCommand saveStatCommand;

	/** The save com flex command. */
	private SaveComMonthCalSetCommand saveMonthCommand;
	
	/** The reference flex pred. */
	private int referenceFlexPred;

}
