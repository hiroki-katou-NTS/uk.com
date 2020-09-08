package nts.uk.ctx.sys.portal.ws.logsettings;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.sys.portal.app.find.logsettings.LogSettingFinder;
import nts.uk.ctx.sys.portal.dom.logsettings.LogSetting;

@Path("sys/portal/logsettings")
@Produces("application/json")
public class LogSettingWebService extends WebService {
	
	@Inject
	private LogSettingFinder logSettingFinder;

	@POST
	@Path("update")
	public void updateLogSetting(LogSetting logSetting) {
		
	}
}
