package nts.uk.ctx.core.ws.socialinsurance.contributionrate;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.core.app.command.socialinsurance.contributionrate.AddContributionRateHistoryCommandHandler;
import nts.uk.ctx.core.app.command.socialinsurance.contributionrate.command.AddContributionRateHistoryCommand;
import nts.uk.ctx.core.app.find.socialinsurance.contributionrate.ContributionRateFinder;
import nts.uk.ctx.core.app.find.socialinsurance.contributionrate.dto.ContributionRateDto;

@Path("ctx/core/socialinsurance/contributionrate")
@Produces("application/json")
public class ContributionRateWebservice {
	@Inject
	private ContributionRateFinder contributionRateFinder;
	@Inject
	private AddContributionRateHistoryCommandHandler addContributionRateHistoryCommandHandler;

	@POST
	@Path("/getByHistoryId/{historyId}")
	public ContributionRateDto getByHistoryId(@PathParam("historyId") String historyId) {
		return this.contributionRateFinder.findContributionRateByHistoryID(historyId);
	}

	@POST
	@Path("/addContributionRateHistory")
	public void addContributionRateHistory(AddContributionRateHistoryCommand command) {
		addContributionRateHistoryCommandHandler.handle(command);
	}
    
}
