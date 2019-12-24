package nts.uk.ctx.pr.core.ws.wageprovision.processdatecls;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls.AddRegisterProcessCommand;
import nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls.AddRegisterProcessCommandHandler;

@Path("ctx.pr.core.ws.wageprovision.processdatecls")
@Produces("application/json")
public class RegisterProcessingScreenWebService {
	@Inject
	private AddRegisterProcessCommandHandler addRegisterProcessCommandHandler;
	@POST
	@Path("registerProcessing")
	public void sendPerformSettingByTime(AddRegisterProcessCommand command) {
		this.addRegisterProcessCommandHandler.handle(command);
	}
}
