package nts.uk.ctx.at.record.ws.kmk004;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.kmk004.s.UpdateUsageUnitSettings;
import nts.uk.ctx.at.record.app.command.kmk004.s.UpdateUsageUnitSettingsInput;

/**
 * 
 * @author chungnt
 *
 */
@Path("at/record/kmk004")
@Produces("application/json")
public class Kmk004WebService extends WebService {

	@Inject
	private UpdateUsageUnitSettings unitSettings;
	
	
	@POST
	@Path("update-setting")
	public void saveStampCardViewA(UpdateUsageUnitSettingsInput command) {
		unitSettings.updateUsageSetting(command);
	}
}
