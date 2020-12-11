package nts.uk.screen.at.ws.knr.knr002.g;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.screen.at.app.query.knr.knr002.g.DisplayTranferContentSettings;
import nts.uk.screen.at.app.query.knr.knr002.g.DisplayTranferContentSettingsDto;

/**
*
* @author xuannt
*
*/
@Path("screen/at/tranfercontentsettings")
@Produces(MediaType.APPLICATION_JSON)
public class DisplayTranferContentSettingsScreenWS {
	@Inject
	private DisplayTranferContentSettings displayTranferContentSettings;
	
	@POST
	@Path("gettimerecordreqsettings/{empInfoTerCode}")
	public DisplayTranferContentSettingsDto getTimeRecordReqSetting(@PathParam("empInfoTerCode") String empInforTerCode) {
		return this.displayTranferContentSettings.getTimeRecordReqSetting(empInforTerCode);
	}
}
