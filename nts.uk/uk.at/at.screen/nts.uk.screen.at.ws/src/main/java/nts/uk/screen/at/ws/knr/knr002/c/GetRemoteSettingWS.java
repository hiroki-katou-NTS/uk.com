package nts.uk.screen.at.ws.knr.knr002.c;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.screen.at.app.query.knr.knr002.a.GetListInfoOfEmpInfoTerminalCommand;
import nts.uk.screen.at.app.query.knr.knr002.c.GetRemoteSettings;
import nts.uk.screen.at.app.query.knr.knr002.c.RemoteSettingsDto;
import nts.uk.screen.at.app.query.knr.knr002.c.UpdateInput;
import nts.uk.screen.at.app.query.knr.knr002.c.UpdateRemoteSettingSc;

@Path("screen/knr002/c")
@Produces(MediaType.APPLICATION_JSON)
public class GetRemoteSettingWS {

	@Inject
	private GetRemoteSettings screenQuery;
	
	@Inject
	private UpdateRemoteSettingSc updateRemoteSettingSC;
	
	@POST
	@Path("getRemoteSettings")
	public RemoteSettingsDto getRemoteSetting(GetListInfoOfEmpInfoTerminalCommand command) {
		return this.screenQuery.getRemoteSettings(command.getEmpInfoTerminalCode());
	}
	
	@POST
	@Path("updateRemoteSetting")
	public void updateRemoteSetting(UpdateInput input) {
		this.updateRemoteSettingSC.updateRemoteSetting(input);
	}
	
}
