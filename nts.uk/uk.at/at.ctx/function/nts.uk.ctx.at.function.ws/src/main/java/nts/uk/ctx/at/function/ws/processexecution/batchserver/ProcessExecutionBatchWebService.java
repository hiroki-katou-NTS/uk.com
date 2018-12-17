package nts.uk.ctx.at.function.ws.processexecution.batchserver;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.arc.task.AsyncTaskInfo;
import nts.uk.ctx.at.function.app.command.processexecution.ExecuteProcessExecutionCommand;
import nts.uk.ctx.at.function.app.command.processexecution.ExecuteProcessExecutionCommandHandler;
import nts.uk.ctx.at.function.app.command.processexecution.TerminateProcessExecutionCommand;
import nts.uk.ctx.at.function.app.command.processexecution.TerminateProcessExecutionCommandHandler;

@Path("batch")
@Produces("application/json")
public class ProcessExecutionBatchWebService extends WebService {
	
	@Inject
	private ExecuteProcessExecutionCommandHandler execHandler;
	
	@Inject
	private TerminateProcessExecutionCommandHandler termHandler;
	
	@POST
	@Path("batch-execute")
	public BatchTaskResult execute(ExecuteProcessExecutionCommand command) {
		AsyncTaskInfo info = this.execHandler.handle(command);
		System.out.println("Run batch service !");
		return new BatchTaskResult(info.getId());
	}

	@POST
	@Path("batch-terminate")
	public BatchTaskResult terminate(TerminateProcessExecutionCommand command) {
		AsyncTaskInfo info = this.termHandler.handle(command);
		return new BatchTaskResult(info.getId());
	}
}
