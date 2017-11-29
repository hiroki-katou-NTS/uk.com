/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.ws.processbatch;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.arc.task.AsyncTaskInfo;
import nts.uk.ctx.at.schedule.app.command.executionlog.ScheduleCreatorExecutionRespone;
import nts.uk.ctx.at.schedule.app.command.processbatch.ScheBatchCorrectExecutionCommand;
import nts.uk.ctx.at.schedule.app.command.processbatch.ScheBatchCorrectExecutionCommandHandler;
import nts.uk.ctx.at.schedule.app.command.processbatch.ScheBatchCorrectExecutionRespone;
import nts.uk.ctx.at.schedule.app.command.processbatch.ScheBatchCorrectSetCheckSaveCommand;
import nts.uk.ctx.at.schedule.app.command.processbatch.ScheBatchCorrectSetCheckSaveCommandHandler;

/**
 * The Class ScheduleBatchCorrectWs.
 */
@Path("ctx/at/schedule/processbatch")
@Produces(MediaType.APPLICATION_JSON)
public class ScheduleBatchCorrectWs extends WebService { 
	
	/** The check. */
	@Inject
	private ScheBatchCorrectSetCheckSaveCommandHandler check;
	
	/** The execution. */
	@Inject
	private ScheBatchCorrectExecutionCommandHandler execution;
	
	
	/**
	 * Check.
	 *
	 * @param command the command
	 */
	@POST
	@Path("check")
	public void check(ScheBatchCorrectSetCheckSaveCommand command) {
		this.check.handle(command);
	}
	
	/**
	 * Execution.
	 *
	 * @param command the command
	 * @return the schedule creator execution respone
	 */
	@POST
	@Path("execution")
	public ScheBatchCorrectExecutionRespone execution(ScheBatchCorrectSetCheckSaveCommand command) {
		AsyncTaskInfo taskInfor = this.execution.handle(command);
		ScheBatchCorrectExecutionRespone respone = new ScheBatchCorrectExecutionRespone();
		respone.setTaskInfor(taskInfor);
		return respone;

	}


}
