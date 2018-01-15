/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.processbatch;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class ScheBatchCorrectExecutionCommand.
 */
@Getter
@Setter
public class ScheBatchCorrectExecutionCommand {
	
	/** The employee id. */
	private String employeeId;
	
	/** The execution id. */
	private String executionId;
	
}
