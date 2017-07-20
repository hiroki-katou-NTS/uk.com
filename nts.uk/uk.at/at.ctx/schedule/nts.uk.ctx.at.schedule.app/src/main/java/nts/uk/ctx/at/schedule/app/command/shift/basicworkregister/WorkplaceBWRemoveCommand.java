/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.basicworkregister;

import lombok.Getter;
import lombok.Setter;


/**
 * The Class WorkplaceBWRemoveCommand.
 */
@Getter
@Setter
public class WorkplaceBWRemoveCommand {

	/** The workplace id. */
	private String workplaceId;
	
	/** The work type code. */
	private String workTypeCode;
}
