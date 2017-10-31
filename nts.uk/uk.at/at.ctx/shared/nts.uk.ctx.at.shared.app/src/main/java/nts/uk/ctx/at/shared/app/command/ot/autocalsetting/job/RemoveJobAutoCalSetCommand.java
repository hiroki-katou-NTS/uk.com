/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.ot.autocalsetting.job;

import lombok.Data;

/**
 * Instantiates a new removes the job auto cal set command.
 */
@Data
public class RemoveJobAutoCalSetCommand {

	/** The job id. */
	private String jobId;
}
