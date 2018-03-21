/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.screen.at.app.kmk004.company.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.app.command.workrecord.monthcal.company.DelComMonthCalSetCommand;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.companyNew.DeleteComStatWorkTimeSetCommand;

/**
 * The Class Kmk004ComDeleteCommand.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Kmk004ComDeleteCommand {

	/** The update com stat work time set command. */
	private DeleteComStatWorkTimeSetCommand delStatCommand;
	
	/** The del month command. */
	private DelComMonthCalSetCommand delMonthCommand;
	
}
