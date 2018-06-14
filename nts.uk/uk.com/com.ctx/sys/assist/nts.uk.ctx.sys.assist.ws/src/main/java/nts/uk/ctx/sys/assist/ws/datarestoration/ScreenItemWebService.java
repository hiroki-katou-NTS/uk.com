package nts.uk.ctx.sys.assist.ws.datarestoration;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.sys.assist.app.find.datarestoration.ScreenItemDto;
import nts.uk.ctx.sys.assist.app.find.datarestoration.ScreenItemFinder;

@Path("ctx/sys/assist/app")
@Produces("application/json")
public class ScreenItemWebService {
	@Inject
	private ScreenItemFinder screenItemFinder;

	@POST
	@Path("findScreenItem/{dataRecoveryProcessId}")
	public ScreenItemDto findScreenItem(@PathParam("dataRecoveryProcessId") String dataRecoveryProcessId) {
		return screenItemFinder.getTargetById(dataRecoveryProcessId);
	}
}
