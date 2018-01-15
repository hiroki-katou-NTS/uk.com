/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.plannedyearholiday.frame;

import java.util.List;

import lombok.Data;

@Data
public class PlanYearHdFrameSaveCommand {
	
	/** The list data. */
	private List<PlanYearHdFrameCommandDto> listData;
	
	/**
	 * Instantiates a new plan year hd frame save command.
	 */
	public PlanYearHdFrameSaveCommand(){
		super();
	}
}
