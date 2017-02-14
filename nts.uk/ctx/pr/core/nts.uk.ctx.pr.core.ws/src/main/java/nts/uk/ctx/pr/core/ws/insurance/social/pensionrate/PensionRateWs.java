package nts.uk.ctx.pr.core.ws.insurance.social.pensionrate;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.insurance.social.pensionrate.command.RegisterPensionCommand;
import nts.uk.ctx.pr.core.app.insurance.social.pensionrate.command.RegisterPensionCommandHandler;
import nts.uk.ctx.pr.core.app.insurance.social.pensionrate.command.UpdatePensionCommand;
import nts.uk.ctx.pr.core.app.insurance.social.pensionrate.command.UpdatePensionCommandHandler;
import nts.uk.ctx.pr.core.app.insurance.social.pensionrate.find.PensionRateDto;
import nts.uk.ctx.pr.core.app.insurance.social.pensionrate.find.PensionRateFinder;

@Path("ctx/pr/core/insurance/social/pensionrate")
@Produces("application/json")
public class PensionRateWs extends WebService {

	@Inject
	private PensionRateFinder pensionRateFinder;
	@Inject
	private RegisterPensionCommandHandler registerPensionCommandHandler;
	@Inject
	private UpdatePensionCommandHandler updatePensionCommandHandler;

	@POST
	@Path("find/{id}")
	public PensionRateDto find(@PathParam("id") String id) {
		return pensionRateFinder.find(id).get();
	}

	@POST
	@Path("create")
	public void create(RegisterPensionCommand command) {
		registerPensionCommandHandler.handle(command);
	}

	@POST
	@Path("update")
	public void update(UpdatePensionCommand command) {
		updatePensionCommandHandler.handle(command);
	}
}
