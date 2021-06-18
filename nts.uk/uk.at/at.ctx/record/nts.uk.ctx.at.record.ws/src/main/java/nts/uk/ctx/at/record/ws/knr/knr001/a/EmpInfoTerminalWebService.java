package nts.uk.ctx.at.record.ws.knr.knr001.a;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.knr.knr001.a.EmpInfoTerminalResgiterAndUpdateCommand;
import nts.uk.ctx.at.record.app.command.knr.knr001.a.EmpInfoTerminalUpdateCommandHandler;
import nts.uk.ctx.at.record.app.command.knr.knr001.a.EmpInfoTerminalDeleteCommand;
import nts.uk.ctx.at.record.app.command.knr.knr001.a.EmpInfoTerminalDeleteCommandHandler;
import nts.uk.ctx.at.record.app.command.knr.knr001.a.EmpInfoTerminalRegisterCommandHandler;

@Path("at/record/empinfoterminal")
@Produces("application/json")
public class EmpInfoTerminalWebService extends WebService {

	@Inject
	private EmpInfoTerminalRegisterCommandHandler registerHandler;
	
	@Inject
	private EmpInfoTerminalUpdateCommandHandler updateHandler;
	
	@Inject
	private EmpInfoTerminalDeleteCommandHandler deleteHandler;
	
	@POST
	@Path("register")
	public void register(EmpInfoTerminalResgiterAndUpdateCommand command) {
		this.registerHandler.handle(command);
	}
	
	@POST
	@Path("update")
	public void update(EmpInfoTerminalResgiterAndUpdateCommand command) {
		this.updateHandler.handle(command);
	}
	
	@POST
	@Path("delete")
	public void delete(EmpInfoTerminalDeleteCommand command) {
		this.deleteHandler.handle(command);
	}
}
