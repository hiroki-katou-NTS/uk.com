package nts.uk.ctx.exio.ws.input;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.exio.app.input.find.setting.ExternalImportSettingDto;
import nts.uk.ctx.exio.app.input.find.setting.ExternalImportSettingFinder;
import nts.uk.ctx.exio.app.input.find.setting.ExternalImportSettingListItemDto;

@Path("exio/input/setting")
@Produces("application/json")
public class ExternalImportSettingWebService {
	
	@Inject
	private ExternalImportSettingFinder finder;
	
	@POST
	@Path("find-all")
	public List<ExternalImportSettingListItemDto> findAll() {
		return finder.findAll();
	}
	
	@POST
	@Path("find")
	public ExternalImportSettingDto find(@PathParam("settingCode") String settingCode) {
		return finder.find(settingCode);
	}
}
