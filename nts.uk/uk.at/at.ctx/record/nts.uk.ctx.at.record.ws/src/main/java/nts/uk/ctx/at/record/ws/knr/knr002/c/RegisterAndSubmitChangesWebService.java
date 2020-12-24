package nts.uk.ctx.at.record.ws.knr.knr002.c;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.knr.knr002.c.RegisterAndSubmitChangesCommand;
import nts.uk.ctx.at.record.app.command.knr.knr002.c.RegisterAndSubmitChangesCommandHandler;

@Path("at/record/knr002/cmd/c")
@Produces("application/json")
public class RegisterAndSubmitChangesWebService extends WebService {

	@Inject
	private RegisterAndSubmitChangesCommandHandler registerAndSubmitChangesCommandHandler;
	
	@POST
	@Path("registerAndSubmit")
	public void registerAndSubmitChanges(RegisterAndSubmitChangesCommand command) {
		this.registerAndSubmitChangesCommandHandler.handle(command);
	}
}
