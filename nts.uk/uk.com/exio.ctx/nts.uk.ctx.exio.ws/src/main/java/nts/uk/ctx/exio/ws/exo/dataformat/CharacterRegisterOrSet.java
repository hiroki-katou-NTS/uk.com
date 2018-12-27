package nts.uk.ctx.exio.ws.exo.dataformat;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.exio.app.command.exo.charegister.ChacDataFmSetCommand;
import nts.uk.ctx.exio.app.command.exo.charegister.SettingDataCharRegisterService;

@Path("exio/exo/character")
@Produces("application/json")
public class CharacterRegisterOrSet {
	
	@Inject
	SettingDataCharRegisterService settingDataCharRegisterService;
	
	@POST
	@Path("add")
	public void addCharacter(ChacDataFmSetCommand command) {
		this.settingDataCharRegisterService.handle(command);
	}

}
