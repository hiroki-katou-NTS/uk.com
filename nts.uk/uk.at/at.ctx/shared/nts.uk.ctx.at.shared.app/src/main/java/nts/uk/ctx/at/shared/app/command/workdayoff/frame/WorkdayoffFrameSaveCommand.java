/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.workdayoff.frame;

import java.util.List;

import lombok.Data;

@Data
public class WorkdayoffFrameSaveCommand {
	
	/** The list data. */
	private List<WorkdayoffFrameCommandDto> listData;
	
	/**
	 * Instantiates a new plan year hd frame save command.
	 */
	public WorkdayoffFrameSaveCommand(){
		super();
	}
}
