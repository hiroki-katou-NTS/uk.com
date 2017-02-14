package nts.uk.ctx.pr.core.ws.insurance.social.healthavgearn;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.insurance.social.healthavgearn.command.UpdateHealthInsuranceAvgearnCommand;
import nts.uk.ctx.pr.core.app.insurance.social.healthavgearn.command.UpdateHealthInsuranceAvgearnCommandHandler;
import nts.uk.ctx.pr.core.app.insurance.social.healthavgearn.find.HealthInsuranceAvgearnDto;
import nts.uk.ctx.pr.core.app.insurance.social.healthavgearn.find.HealthInsuranceAvgearnFinder;

@Path("ctx/pr/core/insurance/social/healthavgearn")
@Produces("application/json")
public class HealthInsuranceAvgearnWs extends WebService {

	@Inject
	private HealthInsuranceAvgearnFinder healthInsuranceAvgearnFinder;
	@Inject
	private UpdateHealthInsuranceAvgearnCommandHandler updateHealthInsuranceAvgearnCommandHandler;

	@POST
	@Path("update")
	public void update(List<UpdateHealthInsuranceAvgearnCommand> commands) {
		commands.forEach(command -> updateHealthInsuranceAvgearnCommandHandler.handle(command));
	}

	@POST
	@Path("find/{id}")
	public List<HealthInsuranceAvgearnDto> find(@PathParam("id") String id) {
		return healthInsuranceAvgearnFinder.find(id);
	}
}
