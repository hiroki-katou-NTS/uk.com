package nts.uk.ctx.pr.core.ws.insurance.social.healthrate;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.insurance.social.healthrate.command.RegisterHealthInsuranceCommand;
import nts.uk.ctx.pr.core.app.insurance.social.healthrate.command.RegisterHealthInsuranceCommandHandler;
import nts.uk.ctx.pr.core.app.insurance.social.healthrate.command.UpdateHealthInsuranceCommand;
import nts.uk.ctx.pr.core.app.insurance.social.healthrate.command.UpdateHealthInsuranceCommandHandler;
import nts.uk.ctx.pr.core.app.insurance.social.healthrate.find.HealthInsuranceRateDto;
import nts.uk.ctx.pr.core.app.insurance.social.healthrate.find.HealthInsuranceRateFinder;

@Path("ctx/pr/core/insurance/social/healthrate")
@Produces("application/json")
public class HealthInsuranceRateWs extends WebService {

	@Inject
	private HealthInsuranceRateFinder healthInsuranceRateFinder;
	@Inject
	private RegisterHealthInsuranceCommandHandler registerHealthInsuranceCommandHandler;
	@Inject
	private UpdateHealthInsuranceCommandHandler updateHealthInsuranceCommandHandler;

	//find by historyId
	@POST
	@Path("find/{id}")
	public HealthInsuranceRateDto find(@PathParam("id") String id) {
		return healthInsuranceRateFinder.find(id).get();
	}
	//find All by OfficeCode
	@POST
	@Path("findByOfficeCode/{officeCode}")
	public List<HealthInsuranceRateDto> findbyCode(@PathParam("officeCode") String officeCode) {
		return healthInsuranceRateFinder.findByOfficeCode(officeCode);
	}
	
	@POST
	@Path("create")
	public void create(RegisterHealthInsuranceCommand command) {
		registerHealthInsuranceCommandHandler.handle(command);
	}

	@POST
	@Path("update")
	public void update(UpdateHealthInsuranceCommand command) {
		updateHealthInsuranceCommandHandler.handle(command);
	}

}
