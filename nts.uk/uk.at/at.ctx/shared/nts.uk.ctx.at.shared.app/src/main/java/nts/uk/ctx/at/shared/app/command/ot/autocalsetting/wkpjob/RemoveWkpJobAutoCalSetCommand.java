/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.ot.autocalsetting.wkpjob;

import lombok.Data;

/**
 * The Class RemoveWkpJobAutoCalSetCommand.
 */
@Data
public class RemoveWkpJobAutoCalSetCommand {

	/** The wkp id. */
	private String wkpId;
	
	/** The job id. */
	private String jobId;
}
