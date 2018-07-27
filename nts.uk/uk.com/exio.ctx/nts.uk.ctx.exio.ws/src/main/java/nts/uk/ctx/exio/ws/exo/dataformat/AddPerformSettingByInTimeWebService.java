package nts.uk.ctx.exio.ws.exo.dataformat;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.exio.app.command.exo.dataformat.AddPerformSettingByInTimeCommand;
import nts.uk.ctx.exio.app.command.exo.dataformat.AddPerformSettingByInTimeCommandHandler;
import nts.uk.ctx.exio.app.find.exo.dataformat.PerformSettingByInTimeDto;
import nts.uk.ctx.exio.app.find.exo.dataformat.PerformSettingByInTimeFinder;
import nts.uk.shr.com.context.AppContexts;

@Path("exio/exo/dataformat")
@Produces("application/json")
public class AddPerformSettingByInTimeWebService {
	@Inject
	private AddPerformSettingByInTimeCommandHandler addPerformSettingByInTimeCommandHandler;
	@Inject
	private PerformSettingByInTimeFinder performSettingByInTimeFinder;

	@POST
	@Path("sendPerformSettingByInTime")
	public void sendPerformSettingByInTime(AddPerformSettingByInTimeCommand command) {
		this.addPerformSettingByInTimeCommandHandler.handle(command);
	}

	@POST
	@Path("findPerformSettingByInTime")
	public PerformSettingByInTimeDto findPerformSettingByInTime() {
		String cid = AppContexts.user().companyId();
		return performSettingByInTimeFinder.getInTimeDataFmSetByCid(cid);
	}
}
