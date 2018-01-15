/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.ot.frame;

import java.util.List;

import lombok.Data;

@Data
public class OvertimeWorkFrameSaveCommand {
	
	/** The list data. */
	private List<OvertimeWorkFrameCommandDto> listData;
	
	/**
	 * Instantiates a new plan year hd frame save command.
	 */
	public OvertimeWorkFrameSaveCommand(){
		super();
	}
}
