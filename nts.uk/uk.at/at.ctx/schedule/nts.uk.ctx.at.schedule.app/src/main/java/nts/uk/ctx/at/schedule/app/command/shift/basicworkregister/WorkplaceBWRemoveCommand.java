/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.basicworkregister;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.schedule.app.command.shift.basicworkregister.dto.WorkplaceBasicWorkDto;


/**
 * The Class WorkplaceBWRemoveCommand.
 */
@Getter
@Setter
public class WorkplaceBWRemoveCommand {

	/** The workplace basic work. */
	private WorkplaceBasicWorkDto workplaceBasicWork;
}
