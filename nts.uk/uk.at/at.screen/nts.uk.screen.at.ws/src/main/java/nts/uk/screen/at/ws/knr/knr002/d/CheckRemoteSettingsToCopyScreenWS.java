package nts.uk.screen.at.ws.knr.knr002.d;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.screen.at.app.query.knr.knr002.d.CheckRemoteSettingsToCopy;
import nts.uk.screen.at.app.query.knr.knr002.d.CheckRemoteSettingsToCopyDto;

/**
 * 
 * @author xuannt
 *
 */
@Path("screen/at/checkremotesetting")
@Produces(MediaType.APPLICATION_JSON)
public class CheckRemoteSettingsToCopyScreenWS {
	
	@Inject
	private CheckRemoteSettingsToCopy checkRemoteSettingsToCopy;
	
	@POST
	@Path("check")
	public void checkRemoteSettingsToCopy(CheckRemoteSettingsToCopyDto dto) {		
		this.checkRemoteSettingsToCopy.checkRemoteSettingsToCopy(dto.getListEmpCode());
	}

}
