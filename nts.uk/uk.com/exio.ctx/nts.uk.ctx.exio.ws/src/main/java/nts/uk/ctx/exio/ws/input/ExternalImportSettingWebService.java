package nts.uk.ctx.exio.ws.input;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.exio.app.input.command.setting.ExternalImportSettingAddCommandHandler;
import nts.uk.ctx.exio.app.input.command.setting.ExternalImportSettingCommand;
import nts.uk.ctx.exio.app.input.find.setting.ExternalImportSettingDto;
import nts.uk.ctx.exio.app.input.find.setting.ExternalImportSettingFinder;
import nts.uk.ctx.exio.app.input.find.setting.ExternalImportSettingListItemDto;

@Path("exio/input/setting")
@Produces("application/json")
public class ExternalImportSettingWebService extends WebService {
	
	@Inject
	private ExternalImportSettingFinder finder;
	
	@Inject
	private ExternalImportSettingAddCommandHandler addCmd;
	
	@POST
	@Path("find-all")
	public List<ExternalImportSettingListItemDto> findAll() {
		return finder.findAll();
	}
	
	@POST
	@Path("find/{settingCode}")
	public ExternalImportSettingDto find(@PathParam("settingCode") String settingCode) {
		ExternalImportSettingDto result = finder.find(settingCode);
		return result;
	}
	
	@POST
	@Path("save")
	public void save(ExternalImportSettingCommand command) {
		addCmd.handle(command);
	}
}
