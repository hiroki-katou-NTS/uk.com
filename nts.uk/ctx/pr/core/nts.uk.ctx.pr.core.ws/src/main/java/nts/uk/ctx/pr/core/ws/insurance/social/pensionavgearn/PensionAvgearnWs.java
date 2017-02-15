package nts.uk.ctx.pr.core.ws.insurance.social.pensionavgearn;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.insurance.social.pensionavgearn.command.UpdatePensionAvgearnCommand;
import nts.uk.ctx.pr.core.app.insurance.social.pensionavgearn.command.UpdatePensionAvgearnCommandHandler;
import nts.uk.ctx.pr.core.app.insurance.social.pensionavgearn.find.PensionAvgearnDto;
import nts.uk.ctx.pr.core.app.insurance.social.pensionavgearn.find.PensionAvgearnFinder;

@Path("ctx/pr/core/insurance/social/pensionavgearn")
@Produces("application/json")
public class PensionAvgearnWs extends WebService {
	@Inject
	private PensionAvgearnFinder pensionAvgearnFinder;
	@Inject
	private UpdatePensionAvgearnCommandHandler updatePensionAvgearnCommandHandler;

	@POST
	@Path("find/{id}")
	public List<PensionAvgearnDto> find(@PathParam("id") String id) {
		return pensionAvgearnFinder.find(id);
	}

	@POST
	@Path("update")
	public void update(UpdatePensionAvgearnCommand command) {
		updatePensionAvgearnCommandHandler.handle(command);
	}
}
