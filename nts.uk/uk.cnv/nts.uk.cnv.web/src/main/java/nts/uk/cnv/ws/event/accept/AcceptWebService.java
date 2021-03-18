package nts.uk.cnv.ws.event.accept;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.cnv.app.td.command.event.accept.AcceptCommand;
import nts.uk.cnv.app.td.command.event.accept.AcceptCommandHandler;

@Path("td/event/accept")
@Produces(MediaType.APPLICATION_JSON)
public class AcceptWebService {
	
	@Inject
	AcceptCommandHandler acceptCommandHandler;
	
	@POST
	@Path("add")
	public void add(AcceptCommand command) {
		acceptCommandHandler.handle(command);
	}
}
