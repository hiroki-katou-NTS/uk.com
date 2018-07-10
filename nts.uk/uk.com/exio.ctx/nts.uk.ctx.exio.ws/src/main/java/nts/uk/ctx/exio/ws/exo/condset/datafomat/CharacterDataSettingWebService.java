package nts.uk.ctx.exio.ws.exo.condset.datafomat;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.exio.app.find.exo.charoutputsetting.SettingItemScreenDTO;
import nts.uk.ctx.exio.app.find.exo.charoutputsetting.SettingTypeScreenFinder;

@Path("exio/exo/char")
@Produces("application/json")
public class CharacterDataSettingWebService {

	@Inject
	SettingTypeScreenFinder settingTypeScreen;
	
	@POST
	@Path("getdatatype")
	public SettingItemScreenDTO getCharDatatype(){
		return settingTypeScreen.getActiveType();
	}
}
