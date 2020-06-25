package nts.uk.ctx.at.record.ws.kdp.kdp010.a;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.kdp.kdp010.a.PortalStampSettingsCommandHandler;
import nts.uk.ctx.at.record.app.command.kdp.kdp010.a.command.PortalStampSettingsCommand;

@Path("at/record/stamp/portalstampsettings")
@Produces("application/json")
public class DipslayPortalStampSettingsWebService extends WebService {

	@Inject
	private PortalStampSettingsCommandHandler portalStampSettingsCommandHandler;
	
	/**打刻の前準備(ポータル)を登録する */
	@POST
	@Path("save")
	public void save(PortalStampSettingsCommand command) {
		portalStampSettingsCommandHandler.save(command);
	}
}


