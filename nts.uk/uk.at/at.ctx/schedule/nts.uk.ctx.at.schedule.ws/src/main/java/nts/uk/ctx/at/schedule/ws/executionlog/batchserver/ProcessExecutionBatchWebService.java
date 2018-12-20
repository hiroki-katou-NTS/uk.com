package nts.uk.ctx.at.schedule.ws.executionlog.batchserver;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.arc.task.AsyncTaskInfo;
import nts.uk.ctx.at.schedule.app.command.executionlog.ScheduleCreatorExecutionCommand;
import nts.uk.ctx.at.schedule.app.command.executionlog.ScheduleCreatorExecutionCommandHandler;

@Path("batch")
@Produces("application/json")
public class ProcessExecutionBatchWebService extends WebService {

	@Inject
	private ScheduleCreatorExecutionCommandHandler execution;

	@POST
	@Path("batch-schedule")
	public BatchTaskResult execute(ScheduleCreatorExecutionCommand command) {
		AsyncTaskInfo info = this.execution.handle(command);
		return new BatchTaskResult(info.getId());
	}
}
