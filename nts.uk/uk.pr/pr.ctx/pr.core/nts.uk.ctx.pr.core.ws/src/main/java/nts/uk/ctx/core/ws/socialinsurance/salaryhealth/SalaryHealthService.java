package nts.uk.ctx.core.ws.socialinsurance.salaryhealth;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.core.app.command.socialinsurance.salaryhealth.HealthInsuStandardMonthlyFinder;
import nts.uk.ctx.core.app.command.socialinsurance.salaryhealth.dto.HealthInsuPerGradeFeeDto;
import nts.uk.ctx.core.app.command.socialinsurance.salaryhealth.dto.SalaryHealthDto;
import nts.uk.ctx.core.app.command.socialinsurance.salaryhealth.dto.StartCommand;

@Path("ctx/pr/core/socialinsurance/salaryhealth")
@Produces("application/json")
public class SalaryHealthService {	
	
	@Inject
	private HealthInsuStandardMonthlyFinder healthInsuStandardMonthlyFinder;
	
	@POST
	@Path("/start")
	public SalaryHealthDto startScreen(StartCommand startCommand) {
		return healthInsuStandardMonthlyFinder.initScreen(startCommand.getDate(), startCommand.getHistoryId());
	}
	
	@POST
	@Path("/update")
	public List<HealthInsuPerGradeFeeDto> update (@PathParam("historyId") String historyId) {
		return healthInsuStandardMonthlyFinder.update(historyId);
	}
	
}
