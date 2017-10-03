/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.executionlog;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLogRepository;

/**
 * The Class ScheduleExecutionLogSaveCommandHandler.
 */
@Stateless
public class ScheduleExecutionLogSaveCommandHandler
		extends CommandHandler<ScheduleExecutionLogSaveCommand> {
	
	/** The repository. */
	@Inject
	private ScheduleExecutionLogRepository repository; 

	@Override
	protected void handle(CommandHandlerContext<ScheduleExecutionLogSaveCommand> context) {
		// TODO Auto-generated method stub
		
	}

}
