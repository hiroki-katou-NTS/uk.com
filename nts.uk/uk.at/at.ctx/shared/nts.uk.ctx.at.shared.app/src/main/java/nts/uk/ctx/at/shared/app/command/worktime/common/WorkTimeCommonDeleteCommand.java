/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.common;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class WorkTimeCommonDeleteCommand.
 */
@Getter
@Setter
public class WorkTimeCommonDeleteCommand {
	
	/** The work time code. */
	private String workTimeCode;
	
	private String langId;
}
