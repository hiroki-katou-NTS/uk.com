package nts.uk.ctx.at.record.ws.workrecord.log.batchserver;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.task.AsyncTaskInfo;
import nts.uk.ctx.at.record.app.command.workrecord.log.CheckProcessCommand;
import nts.uk.ctx.at.record.app.command.workrecord.log.CheckProcessCommandHandler;
import nts.uk.ctx.at.record.ws.workrecord.log.ImplementationResultWebService;

@Path("batch")
@Produces("application/json")
public class ImplResultBatchServerWebService {
	
	@Inject
	private CheckProcessCommandHandler queryExecutionStatusCommandHandler;
	
	@POST
	@Path("task-result")
	public ImplResultDto doTask(CheckProcessCommand command) {
		AsyncTaskInfo info = this.queryExecutionStatusCommandHandler.handle(command);
		return new ImplResultDto(info.getId());
		
	}
}
