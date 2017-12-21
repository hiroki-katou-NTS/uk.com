package nts.uk.ctx.at.record.ws.bonuspay;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.bonuspay.BPUnitUseSettingUpdateCommand;
import nts.uk.ctx.at.record.app.command.bonuspay.BPUnitUseSettingUpdateCommandHandler;
import nts.uk.ctx.at.record.app.find.bonuspay.BPUnitUseSettingDto;
import nts.uk.ctx.at.record.app.find.bonuspay.BPUnitUseSettingFinder;

@Path("at/share/bpUnitUseSetting")
@Produces("application/json")
public class BPUnitUseSettingWebService extends WebService {
	@Inject
	private BPUnitUseSettingFinder bpUnitUseSettingFinder;
	@Inject
	private BPUnitUseSettingUpdateCommandHandler bpUnitUseSettingCommandHandler;

	@POST
	@Path("getSetting")
	public BPUnitUseSettingDto getSetting() {
		return this.bpUnitUseSettingFinder.getSetting();

	}

	@POST
	@Path("updateSetting")
	public void updateSetting(BPUnitUseSettingUpdateCommand bpUnitUseSettingUpdateCommand) {
		this.bpUnitUseSettingCommandHandler.handle(bpUnitUseSettingUpdateCommand);
	}
	

}
