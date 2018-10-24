package nts.uk.ctx.exio.ws.exo.initialcase;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.exio.app.find.exo.initialcasesetting.ItemTypeDTO;
import nts.uk.ctx.exio.app.find.exo.initialcasesetting.SettingInitialCaseFinder;

@Path("exio/exo/initial")
@Produces("application/json")
public class InitialCaseSettingWebService {

	@Inject
	SettingInitialCaseFinder settingInitialCaseFinder;
	
	@POST
	@Path("idsetting")
	public List<ItemTypeDTO> getListId(){
		return settingInitialCaseFinder.getListType();
	}
	
}
