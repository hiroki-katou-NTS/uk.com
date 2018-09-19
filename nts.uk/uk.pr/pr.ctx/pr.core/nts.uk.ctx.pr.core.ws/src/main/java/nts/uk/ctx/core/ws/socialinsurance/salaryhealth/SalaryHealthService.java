package nts.uk.ctx.core.ws.socialinsurance.salaryhealth;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.core.app.command.socialinsurance.salaryhealth.HealthInsuStandardMonthlyFinder;
import nts.uk.ctx.core.app.command.socialinsurance.salaryhealth.dto.ResponseSalaryHealth;
import nts.uk.ctx.core.app.command.socialinsurance.salaryhealth.dto.StartCommand;

@Path("ctx/pr/core/socialinsurance/salaryhealth")
@Produces("application/json")
public class SalaryHealthService {	
	
	@Inject
	private HealthInsuStandardMonthlyFinder healthInsuStandardMonthlyFinder;
	
	@POST
	@Path("/start")
	public ResponseSalaryHealth startScreen(StartCommand startCommand) {
		return healthInsuStandardMonthlyFinder.initScreen(startCommand.getDate(), startCommand.getHistoryId());
	}
	
}
