package nts.uk.ctx.pr.core.ws.socialinsurance.salaryhealth;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.pr.core.app.command.socialinsurance.salaryhealth.ContributionRateHandler;
import nts.uk.ctx.pr.core.app.command.socialinsurance.salaryhealth.HealthInsuCommandHandler;
import nts.uk.ctx.pr.core.app.command.socialinsurance.salaryhealth.HealthInsuStandardMonthlyFinder;
import nts.uk.ctx.pr.core.app.command.socialinsurance.salaryhealth.WelfarePensionStandardMonthlyFeeCommand;
import nts.uk.ctx.pr.core.app.command.socialinsurance.salaryhealth.WelfarePensionStandardMonthlyFeeFinder;
import nts.uk.ctx.pr.core.app.command.socialinsurance.salaryhealth.dto.CusWelfarePensionStandardDto;
import nts.uk.ctx.pr.core.app.command.socialinsurance.salaryhealth.dto.ResponseWelfarePension;
import nts.uk.ctx.pr.core.app.command.socialinsurance.salaryhealth.dto.SalaryHealthDto;
import nts.uk.ctx.pr.core.app.command.socialinsurance.salaryhealth.dto.StartCommandHealth;
import nts.uk.ctx.pr.core.app.command.socialinsurance.salaryhealth.dto.UpdateCommandHealth;
import nts.uk.ctx.pr.core.app.command.socialinsurance.salaryhealth.dto.UpdateCommandWelfare;
import nts.uk.ctx.pr.core.app.command.socialinsurance.salaryhealth.dto.UpdateContributionRateDto;

@Path("ctx/pr/core/socialinsurance/salaryhealth")
@Produces("application/json")
public class SalaryHealthService {	
	
	@Inject
	private HealthInsuStandardMonthlyFinder healthInsuStandardMonthlyFinder;
	
	@Inject
	private HealthInsuCommandHandler healthInsuCommandHandler;
	
	@Inject
	private WelfarePensionStandardMonthlyFeeFinder feeFinder;
	
	@Inject
	private WelfarePensionStandardMonthlyFeeCommand feeCommand;
	
	@Inject
	private ContributionRateHandler contributionRateHandler;
	
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
	public ResponseWelfarePension startScreenWelfare(StartCommandHealth startCommand) {
		return feeFinder.findAllWelfarePensionAndRate(startCommand,false);
	}
	
	@POST
	@Path("/updatewelfare")
	public List<String> updateWelfare(UpdateCommandWelfare updateCommandWelfare) {
		return feeCommand.handle(updateCommandWelfare);
	}
	
	@POST
	@Path("/countwelfare")
	public ResponseWelfarePension countWelfare(StartCommandHealth startCommand) {
		return feeFinder.findAllWelfarePensionAndRate(startCommand,true);
	}
	
	@POST
	@Path("/startwelfarestandard")
	public List<CusWelfarePensionStandardDto> startWelfareStandardMonthly(StartCommandHealth startCommand) {
		return feeFinder.findAllWelfarePensionAndContributionRate(startCommand, false);
	}
	
	@POST
	@Path("/updatewelfarestandard")
	public List<String> updateWelfareStandard(UpdateContributionRateDto updateContributionRateDto) {
		return contributionRateHandler.handle(updateContributionRateDto);
	}
	
	@POST
	@Path("/countwelfarestandard")
	public List<CusWelfarePensionStandardDto> countWelfareStandardMonthly(StartCommandHealth startCommand) {
		return feeFinder.findAllWelfarePensionAndContributionRate(startCommand, true);
	}
}
