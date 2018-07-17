package nts.uk.ctx.exio.ws.exo.dataformat;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.exio.app.command.exo.awdataformat.AWDataFormatCommand;
import nts.uk.ctx.exio.app.command.exo.awdataformat.SettingDataAWRegisterService;

@Path("exio/exo/aw")
@Produces("application/json")
public class AWRegisterOrSet {

	@Inject
	SettingDataAWRegisterService settingDataAWRegisterService;
	
	@POST
	@Path("add")
	public void addAW(AWDataFormatCommand command) {
		this.settingDataAWRegisterService.handle(command);
	}
}
