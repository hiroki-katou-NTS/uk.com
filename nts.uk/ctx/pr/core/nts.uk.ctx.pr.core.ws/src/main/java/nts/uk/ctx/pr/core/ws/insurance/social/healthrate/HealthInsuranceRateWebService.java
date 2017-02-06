package nts.uk.ctx.pr.core.ws.insurance.social.healthrate;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.find.insurance.social.healthrate.AvgEarnLevelMasterSettingDto;
import nts.uk.ctx.pr.core.app.find.insurance.social.healthrate.AvgEarnLevelMasterSettingFinder;
import nts.uk.ctx.pr.core.app.find.insurance.social.healthrate.HealthInsuranceAvgearnDto;

@Path("ctx/pr/core/insurance/social/healthrate")
@Produces("application/json")
public class HealthInsuranceRateWebService extends WebService {

	@Inject
	private AvgEarnLevelMasterSettingFinder avgEarnLevelMasterSettingFinder;

	@POST
	@Path("getAvgEarnLevelMasterSettingList")
	public List<AvgEarnLevelMasterSettingDto> getAvgEarnLevelMasterSettingList() {
		return avgEarnLevelMasterSettingFinder.findAll();
	}

	@POST
	@Path("findAllHealthInsuranceAvgearn")
	public List<HealthInsuranceAvgearnDto> findAllHealthInsuranceAvgearn() {
		return null;
	}
}
