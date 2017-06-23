package nts.uk.ctx.at.shared.ws.bonuspay;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.BPUnitUseSettingUpdateCommandHandler;
import nts.uk.ctx.at.shared.app.command.BPUnitUseSettingUpdateCommand;
import nts.uk.ctx.at.shared.app.find.bonuspay.BPUnitUseSettingDto;
import nts.uk.ctx.at.shared.app.find.bonuspay.BPUnitUseSettingFinder;

@Path("at/share/bpUnitUseSetting")
@Produces("application/json")
public class BPUnitUseSettingWebService extends WebService {
	@Inject
	private BPUnitUseSettingFinder bpUnitUseSettingFinder;
	@Inject
	private BPUnitUseSettingUpdateCommandHandler  bpUnitUseSettingCommandHandler;
	
	@POST
	@Path("getSetting/{companyId}")
	public BPUnitUseSettingDto getSetting(@PathParam("companyId") String companyId){
		return this.bpUnitUseSettingFinder.getSetting(companyId);
		
	}
	@POST
	@Path("update")
	void updateSetting(BPUnitUseSettingUpdateCommand bpUnitUseSettingUpdateCommand){
		this.bpUnitUseSettingCommandHandler.handle(bpUnitUseSettingUpdateCommand);
	}
	
}


