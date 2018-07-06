package nts.uk.ctx.exio.ws.exo.condset.datafomat;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.exio.app.command.exo.charegister.SettingDataCharRegisterService;
import nts.uk.ctx.exio.app.command.exo.charegister.SettingDataCharUpdateService;
import nts.uk.ctx.exio.app.command.exo.datafomat.ChacDataFmSetCommand;

@Path("exio/exo/character")
@Produces("application/json")
public class CharacterRegisterOrSet {
	
	@Inject
	SettingDataCharRegisterService settingDataCharRegisterService;
	
	@Inject
	SettingDataCharUpdateService settingDataCharUpdateService;
	
	@POST
	@Path("add")
	public void addCharacter(ChacDataFmSetCommand command) {
		this.settingDataCharRegisterService.handle(command);
	}
	
	@POST
	@Path("update")
	public void updateCharacter(ChacDataFmSetCommand command) {
		this.settingDataCharUpdateService.handle(command);
	}

}
