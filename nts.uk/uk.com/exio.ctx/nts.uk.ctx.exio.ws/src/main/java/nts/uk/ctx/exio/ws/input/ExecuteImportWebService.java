package nts.uk.ctx.exio.ws.input;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.task.AsyncTaskInfo;
import nts.uk.ctx.exio.app.input.execute.ExternalImportExecuteCommand;
import nts.uk.ctx.exio.app.input.execute.ExternalImportExecuteCommandHandler;
import nts.uk.ctx.exio.app.input.prepare.ExternalImportPrepareCommand;
import nts.uk.ctx.exio.app.input.prepare.ExternalImportPrepareCommandHandler;

@Path("exio/input")
@Produces("application/json")
public class ExecuteImportWebService {
	
	@Inject
	private ExternalImportPrepareCommandHandler prepare;

	@POST
	@Path("prepare")
	public AsyncTaskInfo prepare(ExternalImportPrepareCommand command) {
		return prepare.handle(command);
	}
	
	@Inject
	private ExternalImportExecuteCommandHandler execute;

	@POST
	@Path("execute")
	public AsyncTaskInfo execute(ExternalImportExecuteCommand command) {
		return execute.handle(command);
	}
}
