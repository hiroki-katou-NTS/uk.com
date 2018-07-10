package nts.uk.ctx.exio.ws.exo.dataformat;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.exio.app.command.exo.datafomat.AddPerformSettingByTimeCommand;
import nts.uk.ctx.exio.app.command.exo.datafomat.AddPerformSettingByTimeCommandHandler;

@Path("exio/exo/dataformat")
@Produces("application/json")
public class AddPerformSettingByTimeWebService {
	@Inject
	private AddPerformSettingByTimeCommandHandler addPerformSettingByTimeCommandHandler;
	
	@POST
	@Path("sendPerformSettingByTime")
	public void sendPerformSettingByTime(AddPerformSettingByTimeCommand command) {
		this.addPerformSettingByTimeCommandHandler.handle(command);
	}
}
