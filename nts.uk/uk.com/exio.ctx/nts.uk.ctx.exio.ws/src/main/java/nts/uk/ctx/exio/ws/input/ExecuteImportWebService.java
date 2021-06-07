package nts.uk.ctx.exio.ws.input;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.exio.app.command.input.transfer.TransferCanonicalDataCommand;
import nts.uk.ctx.exio.app.command.input.transfer.TransferCanonicalDataCommandHandler;

@Path("input")
@Produces("application/json")
public class ExecuteImportWebService {
	
	@Inject
	TransferCanonicalDataCommandHandler handler;

	@POST
	@Path("execute")
	public void execute(TransferCanonicalDataCommand command) {
		handler.handle(command);
	}
}
