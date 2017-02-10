package nts.uk.ctx.pr.core.ws.insurance.social.pensionrate;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.insurance.social.pensionrate.find.PensionAvgearnDto;
import nts.uk.ctx.pr.core.app.insurance.social.pensionrate.find.PensionAvgearnFinder;
import nts.uk.ctx.pr.core.app.insurance.social.pensionrate.find.PensionRateDto;
import nts.uk.ctx.pr.core.app.insurance.social.pensionrate.find.PensionRateFinder;

@Path("ctx/pr/core/insurance/social/pensionrate")
@Produces("application/json")
public class PensionRateWs extends WebService {
	@Inject
	private PensionRateFinder pensionRateFinder;
	@Inject
	private PensionAvgearnFinder pensionAvgearnFinder;

	@POST
	@Path("findPensionRate/{id}")
	public PensionRateDto findHealthInsuranceRate(@PathParam("id") String id) {
		return pensionRateFinder.find(id).get();
	}

	@POST
	@Path("findPensionAvgearn/{id}")
	public List<PensionAvgearnDto> findPensionAvgearn(@PathParam("id") String id) {
		return pensionAvgearnFinder.find(id);
	}
}
