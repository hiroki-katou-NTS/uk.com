/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.workrecord.workfixed;

import lombok.Getter;

/**
 * Gets the work place id.
 *
 * @return the work place id
 */
@Getter
public class WorkFixedRemoveCommand {

	/** The closure id. */
	private Integer closureId;
	
	/** The work place id. */
	private String workPlaceId;
}
