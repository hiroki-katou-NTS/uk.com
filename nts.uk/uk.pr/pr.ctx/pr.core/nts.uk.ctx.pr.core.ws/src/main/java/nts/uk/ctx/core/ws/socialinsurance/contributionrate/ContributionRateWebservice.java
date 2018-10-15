package nts.uk.ctx.core.ws.socialinsurance.contributionrate;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.core.app.command.socialinsurance.contributionrate.AddContributionRateHistoryCommandHandler;
import nts.uk.ctx.core.app.command.socialinsurance.contributionrate.CheckContributionRateHistoryCommandHandler;
import nts.uk.ctx.core.app.command.socialinsurance.contributionrate.DeleteContributionRateCommandHandler;
import nts.uk.ctx.core.app.command.socialinsurance.contributionrate.EditContributionRateCommnadHandler;
import nts.uk.ctx.core.app.command.socialinsurance.contributionrate.command.AddContributionRateHistoryCommand;
import nts.uk.ctx.core.app.command.socialinsurance.contributionrate.command.CheckContributionRateHistoryCommand;
import nts.uk.ctx.core.app.find.socialinsurance.contributionrate.ContributionRateFinder;
import nts.uk.ctx.core.app.find.socialinsurance.contributionrate.dto.ContributionRateDto;

@Path("ctx/core/socialinsurance/contributionrate")
@Produces("application/json")
public class ContributionRateWebservice {
	@Inject
	private ContributionRateFinder contributionRateFinder;
	@Inject
	private AddContributionRateHistoryCommandHandler addContributionRateHistoryCommandHandler;
	@Inject
	private CheckContributionRateHistoryCommandHandler checkContributionRateHistoryCommandHandler;
	@Inject
	private EditContributionRateCommnadHandler editContributionRateCommnadHandler;
	@Inject
	private DeleteContributionRateCommandHandler deleteContributionRateCommnadHandler;

	@POST
	@Path("/getByHistoryId/{historyId}/{socialInsuranceCode}")
	public ContributionRateDto getByHistoryId(@PathParam("historyId") String historyId,@PathParam("socialInsuranceCode") String socialInsuranceCode) {
		return this.contributionRateFinder.findContributionRateByHistoryID(historyId,socialInsuranceCode);
	}

	@POST
	@Path("/addContributionRateHistory")
	public void addContributionRateHistory(AddContributionRateHistoryCommand command) {
		addContributionRateHistoryCommandHandler.handle(command);
	}
	
	@POST
	@Path("/checkContributionRateHistory")
	public boolean checkContributionRateHistory(CheckContributionRateHistoryCommand command) {
		return checkContributionRateHistoryCommandHandler.handle(command);
	}

	@POST
	@Path("/editHistory")
	public void editContributionRateHistory(AddContributionRateHistoryCommand command) {
		editContributionRateCommnadHandler.handle(command);
	}

	@POST
	@Path("/deleteHistory")
	public void deleteContributionRateHistory(AddContributionRateHistoryCommand command) {
		deleteContributionRateCommnadHandler.handle(command);
	}

}
