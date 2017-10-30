/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.executionlog;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreateContent;

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
	
	/** The company id. */
	private String companyId;
	
	/** The to date. */
	private Date toDate;
	
	/** The content. */
	private ScheduleCreateContent content;
}
