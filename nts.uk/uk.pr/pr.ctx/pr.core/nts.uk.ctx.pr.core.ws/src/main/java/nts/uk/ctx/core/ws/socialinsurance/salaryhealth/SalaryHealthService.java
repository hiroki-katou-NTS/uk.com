package nts.uk.ctx.core.ws.socialinsurance.salaryhealth;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.core.app.command.socialinsurance.salaryhealth.HealthInsuCommandHandler;
import nts.uk.ctx.core.app.command.socialinsurance.salaryhealth.HealthInsuStandardMonthlyFinder;
import nts.uk.ctx.core.app.command.socialinsurance.salaryhealth.dto.SalaryHealthDto;
import nts.uk.ctx.core.app.command.socialinsurance.salaryhealth.dto.StartCommandHealth;
import nts.uk.ctx.core.app.command.socialinsurance.salaryhealth.dto.UpdateCommandHealth;

@Path("ctx/pr/core/socialinsurance/salaryhealth")
@Produces("application/json")
public class SalaryHealthService {	
	
	@Inject
	private HealthInsuStandardMonthlyFinder healthInsuStandardMonthlyFinder;
	
	@Inject
	private HealthInsuCommandHandler healthInsuCommandHandler;
	
	
	
	@POST
	@Path("/start")
	public SalaryHealthDto startScreen(StartCommandHealth startCommand) {
		return healthInsuStandardMonthlyFinder.initScreen(startCommand.getDate(), startCommand.getHistoryId(),false);
	}
	
	@POST
	@Path("/update")
	public List<String> update (UpdateCommandHealth updateCommand) {
		return healthInsuCommandHandler.handle(updateCommand);
	}
	
	@POST
	@Path("/count")
	public SalaryHealthDto countHealthInsu(StartCommandHealth startCommand) {
		return healthInsuStandardMonthlyFinder.initScreen(startCommand.getDate(), startCommand.getHistoryId(),true);
	}
	
	@POST
	@Path("/startwelfare")
	public void startScreenWelfare(StartCommandHealth startCommand) {
		
	}
	
}
