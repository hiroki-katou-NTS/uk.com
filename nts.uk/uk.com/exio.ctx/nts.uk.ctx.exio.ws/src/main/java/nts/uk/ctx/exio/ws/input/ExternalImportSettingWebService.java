package nts.uk.ctx.exio.ws.input;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.exio.app.input.command.setting.RemoveExternalImportSettingCommand;
import nts.uk.ctx.exio.app.input.command.setting.RemoveExternalImportSettingCommandHandler;

@Path("exio/input/setting")
@Produces("application/json")
public class ExternalImportSettingWebService extends WebService {
	
	@Inject
	private RemoveExternalImportSettingCommandHandler removeCmd;
	
	@POST
	@Path("remove")
	public void remove(RemoveExternalImportSettingCommand command) {
		removeCmd.handle(command);
	}
}
