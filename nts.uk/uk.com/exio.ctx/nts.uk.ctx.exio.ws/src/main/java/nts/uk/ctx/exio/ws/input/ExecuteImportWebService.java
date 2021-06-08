package nts.uk.ctx.exio.ws.input;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.exio.app.input.execute.ExternalImportExecuteCommand;
import nts.uk.ctx.exio.app.input.execute.ExternalImportExecuteCommandHandler;

@Path("exio/input")
@Produces("application/json")
public class ExecuteImportWebService {
	
	@Inject
	ExternalImportExecuteCommandHandler handler;

	@POST
	@Path("execute")
	public void execute(ExternalImportExecuteCommand command) {
		handler.handle(command);
	}
}
