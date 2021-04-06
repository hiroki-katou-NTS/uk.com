package nts.uk.screen.at.ws.ktg031;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.ktg031.updateautoexecerrorcheck.UpdateAutoExecErrorCheck;
import nts.uk.shr.com.context.AppContexts;

@Path("screen/at/ktg/ktg031")
@Produces("application/json")
public class Ktg031Ws extends WebService {

	private UpdateAutoExecErrorCheck updateAutoExecErrorCheck;

	/**
	 * 起動する
	 */
	@POST
	@Path("check-update-Auto-exec-error")
	public void check() {
		this.updateAutoExecErrorCheck.check(AppContexts.user().companyId());
	}
}
