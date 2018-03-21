/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.screen.at.app.kmk004.company.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.companyNew.SaveComStatWorkTimeSetCommand;


/**
 * The Class Kmk004ComAddCommand.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Kmk004ComAddCommand {

	/** The save com stat work time set command. */
	private SaveComStatWorkTimeSetCommand saveCommand;

}
