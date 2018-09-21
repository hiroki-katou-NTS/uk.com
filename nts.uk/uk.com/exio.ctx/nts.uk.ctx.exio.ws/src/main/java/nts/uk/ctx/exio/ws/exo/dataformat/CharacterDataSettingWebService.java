package nts.uk.ctx.exio.ws.exo.dataformat;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.exio.app.find.exo.charoutputsetting.GetCdConvertName;
import nts.uk.ctx.exio.app.find.exo.charoutputsetting.SettingItemScreenDTO;
import nts.uk.ctx.exio.app.find.exo.charoutputsetting.SettingTypeScreenService;

@Path("exio/exo/char")
@Produces("application/json")
public class CharacterDataSettingWebService {

	@Inject
	SettingTypeScreenService settingTypeScreen;
	
	@Inject
	GetCdConvertName getCdConvertName;
	
	@POST
	@Path("getdatatype")
	public SettingItemScreenDTO getCharDatatype(){
		return settingTypeScreen.getActiveType();
	}
	
	@POST
	@Path("getcconvertname/{cdConvertCode}")
	public String getCdConvertName(@PathParam("cdConvertCode") String cdConvertCode){
		return getCdConvertName.getCdConvertName(cdConvertCode);
	}
}
