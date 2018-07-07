package nts.uk.ctx.exio.ws.exo.condset.datafomat;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.exio.app.find.exo.charoutputsetting.OutputTypeSettingCommand;
import nts.uk.ctx.exio.app.find.exo.charoutputsetting.SettingItemScreenCommand;
import nts.uk.ctx.exio.app.find.exo.charoutputsetting.SettingTypeScreen;

@Path("exio/exo/char")
@Produces("application/json")
public class CharacterDataSettingWebService {

	@Inject
	SettingTypeScreen settingTypeScreen;
	
	@POST
	@Path("getdatatype")
	public SettingItemScreenCommand getCharDatatype(OutputTypeSettingCommand command){
		return settingTypeScreen.getActiveType(command);
	}
}
