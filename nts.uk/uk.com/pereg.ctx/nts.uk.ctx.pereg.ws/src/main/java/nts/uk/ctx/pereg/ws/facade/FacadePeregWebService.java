package nts.uk.ctx.pereg.ws.facade;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pereg.app.command.facade.PeregCommandFacade;
import nts.uk.shr.pereg.app.command.PeregDeleteCommand;
import nts.uk.shr.pereg.app.command.PeregInputContainer;

@Path("/facade/pereg")
@Produces("application/json")
public class FacadePeregWebService extends WebService {


	@Inject
	private PeregCommandFacade commandFacade;
	
	@POST
	@Path("delete")
	public void delete(PeregDeleteCommand deleteCommand) {
		this.commandFacade.deleteHandler(deleteCommand);
	}
	
	@POST
	@Path("register")
	public Object register(PeregInputContainer inputContainer) {
		return commandFacade.registerHandler(inputContainer);
	}
}
