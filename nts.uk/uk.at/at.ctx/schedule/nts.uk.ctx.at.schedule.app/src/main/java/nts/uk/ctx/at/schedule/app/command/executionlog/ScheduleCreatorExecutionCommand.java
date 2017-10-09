/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.executionlog;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class ScheduleCreatorExecutionCommand.
 */
@Getter
@Setter
public class ScheduleCreatorExecutionCommand {
	
	/** The employee id. */
	private String employeeId;
	
	/** The execution id. */
	private String executionId;
	
	/** The implement atr. */
	private Integer implementAtr;
	
	/** The re create atr. */
	private Integer reCreateAtr;
	
}
