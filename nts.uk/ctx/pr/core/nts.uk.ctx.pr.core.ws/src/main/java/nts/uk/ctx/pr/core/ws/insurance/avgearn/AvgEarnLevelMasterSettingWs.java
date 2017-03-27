package nts.uk.ctx.pr.core.ws.insurance.avgearn;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.insurance.avgearn.find.AvgEarnLevelMasterSettingDto;
import nts.uk.ctx.pr.core.app.insurance.avgearn.find.AvgEarnLevelMasterSettingFinder;

@Path("ctx/pr/core/insurance/avgearnmaster")
@Produces("application/json")
public class AvgEarnLevelMasterSettingWs extends WebService {
	@Inject
	private AvgEarnLevelMasterSettingFinder avgEarnLevelMasterSettingFinder;

	@POST
	@Path("find")
	public List<AvgEarnLevelMasterSettingDto> find() {
		return avgEarnLevelMasterSettingFinder.findAll();
	}

}
