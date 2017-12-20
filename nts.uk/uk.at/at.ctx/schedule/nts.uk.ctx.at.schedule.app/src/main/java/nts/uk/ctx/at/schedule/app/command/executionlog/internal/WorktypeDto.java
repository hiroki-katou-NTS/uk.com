/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.executionlog.internal;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeSet;

/**
 * The Class WorktypeDto.
 */

@Getter
public class WorktypeDto {

	/** The worktype code. */
	private String worktypeCode;

	/** The worktype set. */
	private WorkTypeSet worktypeSet;

	/**
	 * Instantiates a new worktype dto.
	 *
	 * @param worktypeCode the worktype code
	 * @param worktypeSet the worktype set
	 */
	public WorktypeDto(String worktypeCode, WorkTypeSet worktypeSet) {
		super();
		this.worktypeCode = worktypeCode;
		this.worktypeSet = worktypeSet;
	}
}
