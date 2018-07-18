package nts.uk.ctx.exio.ws.exo.dataformat;

import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.exio.app.find.exo.charoutputsetting.SettingItemScreenDTO;
import nts.uk.ctx.exio.app.find.exo.charoutputsetting.SettingTypeScreenService;

@Path("exio/exo/char")
@Produces("application/json")
public class CharacterDataSettingWebService {

	@Inject
	SettingTypeScreenService settingTypeScreen;
	
	@POST
	@Path("getdatatype")
	public Optional<SettingItemScreenDTO> getCharDatatype(){
		return settingTypeScreen.getActiveType();
	}
}
