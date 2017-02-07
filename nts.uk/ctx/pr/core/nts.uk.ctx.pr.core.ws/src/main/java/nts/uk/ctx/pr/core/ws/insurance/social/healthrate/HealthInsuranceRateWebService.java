package nts.uk.ctx.pr.core.ws.insurance.social.healthrate;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.find.insurance.social.healthrate.AvgEarnLevelMasterSettingDto;
import nts.uk.ctx.pr.core.app.find.insurance.social.healthrate.AvgEarnLevelMasterSettingFinder;
import nts.uk.ctx.pr.core.app.find.insurance.social.healthrate.HealthInsuranceAvgearnDto;
import nts.uk.ctx.pr.core.app.find.insurance.social.healthrate.HealthInsuranceAvgearnFinder;
import nts.uk.ctx.pr.core.app.find.insurance.social.healthrate.HealthInsuranceRateDto;
import nts.uk.ctx.pr.core.app.find.insurance.social.healthrate.HealthInsuranceRateFinder;

@Path("ctx/pr/core/insurance/social/healthrate")
@Produces("application/json")
public class HealthInsuranceRateWebService extends WebService {

	@Inject
	private AvgEarnLevelMasterSettingFinder avgEarnLevelMasterSettingFinder;
	@Inject
	private HealthInsuranceRateFinder healthInsuranceRateFinder;
	@Inject
	private HealthInsuranceAvgearnFinder healthInsuranceAvgearnFinder;

	@POST
	@Path("getAvgEarnLevelMasterSettingList")
	public List<AvgEarnLevelMasterSettingDto> getAvgEarnLevelMasterSettingList() {
		return avgEarnLevelMasterSettingFinder.findAll();
	}

	@POST
	@Path("findHealthInsuranceAvgearn/{id}")
	public List<HealthInsuranceAvgearnDto> findHealthInsuranceAvgearn(@PathParam("id") String id) {
		return healthInsuranceAvgearnFinder.find(id);
	}

	@POST
	@Path("findHealthInsuranceRate/{id}")
	public HealthInsuranceRateDto findHealthInsuranceRate(@PathParam("id") String id) {
		return healthInsuranceRateFinder.find(id);
	}
}
