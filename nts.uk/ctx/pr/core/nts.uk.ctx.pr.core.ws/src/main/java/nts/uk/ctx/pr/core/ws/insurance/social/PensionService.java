package nts.uk.ctx.pr.core.ws.insurance.social;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import nts.uk.ctx.pr.core.app.insurance.social.pension.command.RegisterPensionCommand;
import nts.uk.ctx.pr.core.app.insurance.social.pension.command.RegisterPensionCommandHandler;

public class PensionService {
	@Inject
	private RegisterPensionCommandHandler registerPensionCommandHandler;

	@POST
	@Path("pension/list")
	public void listPensionItem(RegisterPensionCommand command)
	{
		this.registerPensionCommandHandler.handle(command);
		return;
	}

}
