package nts.uk.screen.at.ws.kdp.kdp010.a;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.query.kdp.kdp001.a.PortalStampSettingsDto;
import nts.uk.screen.at.app.query.kdp.kdp010.a.PortalStampSettingsFinder;

@Path("at/record/stamp/portalstampsettings")
@Produces("application/json")
public class DipslayPortalStampSettingsWebService extends WebService {

	@Inject
	private PortalStampSettingsFinder portalStampSettingsFinder;
	
	/**打刻の前準備(ポータル)を表示する  */
	@POST
	@Path("get")
	public PortalStampSettingsDto getEmployeeStampData() {
		return portalStampSettingsFinder.get().orElse(null);
	}
}


