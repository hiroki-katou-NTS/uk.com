package nts.uk.ctx.pr.core.ws.insurance.social;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.insurance.social.healthrate.find.AvgEarnLevelMasterSettingDto;
import nts.uk.ctx.pr.core.app.insurance.social.healthrate.find.AvgEarnLevelMasterSettingFinder;

@Path("ctx/pr/core/insurance/social/")
@Produces("application/json")
public class AvgEarnLevelMasterSettingWs extends WebService {
	@Inject
	private AvgEarnLevelMasterSettingFinder avgEarnLevelMasterSettingFinder;

	@POST
	@Path("getAvgEarnLevelMasterSettingList")
	public List<AvgEarnLevelMasterSettingDto> getAvgEarnLevelMasterSettingList() {
		return avgEarnLevelMasterSettingFinder.findAll();
	}

}
