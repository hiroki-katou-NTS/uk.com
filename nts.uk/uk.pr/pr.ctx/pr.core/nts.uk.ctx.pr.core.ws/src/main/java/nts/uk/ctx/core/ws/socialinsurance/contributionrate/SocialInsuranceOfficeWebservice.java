package nts.uk.ctx.core.ws.socialinsurance.contributionrate;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.core.app.find.socialinsurance.contributionrate.ContributionRateFinder;
import nts.uk.ctx.core.app.find.socialinsurance.contributionrate.dto.SocialInsuranceOfficeDto;

@Path("ctx/core/socialinsurance/contributionrate")
@Produces("application/json")
public class SocialInsuranceOfficeWebservice {
	
	@Inject
	private ContributionRateFinder contributionRateFinder;
	
	@POST
	@Path("/getAll")
	public List<SocialInsuranceOfficeDto> getAll() {
		 return contributionRateFinder.findOfficeByCompanyId();
	}
}
