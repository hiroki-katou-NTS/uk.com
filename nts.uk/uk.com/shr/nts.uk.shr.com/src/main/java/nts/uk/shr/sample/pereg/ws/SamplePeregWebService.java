package nts.uk.shr.sample.pereg.ws;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.shr.pereg.app.command.PeregCommandFacade;
import nts.uk.shr.pereg.app.command.PeregInputContainer;

@Path("/sample/pereg")
@Produces("application/json")
public class SamplePeregWebService extends WebService {

	//@Inject
	private PeregCommandFacade commandFacade;
	
	@POST
	@Path("update")
	public void update(PeregInputContainer inputContainer) {
		this.commandFacade.update(inputContainer);
	}
}
