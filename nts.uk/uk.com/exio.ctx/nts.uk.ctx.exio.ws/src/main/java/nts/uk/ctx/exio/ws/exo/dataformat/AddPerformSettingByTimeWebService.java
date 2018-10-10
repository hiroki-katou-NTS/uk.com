package nts.uk.ctx.exio.ws.exo.dataformat;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.exio.app.command.exo.dataformat.AddPerformSettingByTimeCommand;
import nts.uk.ctx.exio.app.command.exo.dataformat.AddPerformSettingByTimeCommandHandler;
import nts.uk.ctx.exio.app.find.exo.dataformat.PerformSettingByTimeDto;
import nts.uk.ctx.exio.app.find.exo.dataformat.PerformSettingByTimeFinder;
import nts.uk.shr.com.context.AppContexts;

@Path("exio/exo/dataformat")
@Produces("application/json")
public class AddPerformSettingByTimeWebService {
	@Inject
	private AddPerformSettingByTimeCommandHandler addPerformSettingByTimeCommandHandler;
	@Inject
	private PerformSettingByTimeFinder performSettingByTimeFinder;
	@POST
	@Path("sendPerformSettingByTime")
	public void sendPerformSettingByTime(AddPerformSettingByTimeCommand command) {
		this.addPerformSettingByTimeCommandHandler.handle(command);
	}	
	
	@POST
	@Path("findPerformSettingByTime")
	public PerformSettingByTimeDto findPerformSettingByTime() {
		String cid = AppContexts.user().companyId();
		return performSettingByTimeFinder.getTimeDataFmSetByCid(cid);
	}
}
