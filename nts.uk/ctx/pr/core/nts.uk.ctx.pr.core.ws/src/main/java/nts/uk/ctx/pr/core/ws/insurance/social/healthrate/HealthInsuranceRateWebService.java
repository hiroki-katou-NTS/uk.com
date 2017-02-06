package nts.uk.ctx.pr.core.ws.insurance.social.healthrate;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;

@Path("ctx/pr/core/insurance/social/healthrate")
@Produces("application/json")
public class HealthInsuranceRateWebService extends WebService {
	@POST
	@Path("getAvgEarnLevelMasterSetting")
	public String getAvgEarnLevelMasterSetting() {
		return "hi";
	}
}
