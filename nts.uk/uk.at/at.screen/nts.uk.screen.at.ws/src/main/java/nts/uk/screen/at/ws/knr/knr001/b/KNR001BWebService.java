package nts.uk.screen.at.ws.knr.knr001.b;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.query.scherec.dailyattdcal.declare.FilingSettingsDto;
import nts.uk.screen.at.app.knr002.query.GetFilingSettingsScreenQuery;
import nts.uk.shr.com.context.AppContexts;

@Path("screen/at/knr001/b")
@Produces(MediaType.APPLICATION_JSON)
public class KNR001BWebService extends WebService {
	
	@Inject
	private GetFilingSettingsScreenQuery getFilingSettingsScreenQuery;

	@POST
	@Path("get-filing-settings")
	public FilingSettingsDto getData() {
		String companyId = AppContexts.user().companyId();
		return getFilingSettingsScreenQuery.get(companyId);
	}
}
