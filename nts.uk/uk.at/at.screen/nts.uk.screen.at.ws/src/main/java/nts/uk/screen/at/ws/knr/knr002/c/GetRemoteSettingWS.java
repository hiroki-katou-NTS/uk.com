package nts.uk.screen.at.ws.knr.knr002.c;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.screen.at.app.query.knr.knr002.a.GetListInfoOfEmpInfoTerminalCommand;
import nts.uk.screen.at.app.query.knr.knr002.c.GetRemoteSettings;
import nts.uk.screen.at.app.query.knr.knr002.c.RemoteSettingsDto;

@Path("screen/knr002/c")
@Produces(MediaType.APPLICATION_JSON)
public class GetRemoteSettingWS {

	@Inject
	private GetRemoteSettings screenQuery;
	
	@POST
	@Path("getRemoteSettings")
	public List<RemoteSettingsDto> getRemoteSetting(GetListInfoOfEmpInfoTerminalCommand command) {
		return this.screenQuery.getRemoteSettings(command.getEmpInfoTerminalCode());
	}
}
