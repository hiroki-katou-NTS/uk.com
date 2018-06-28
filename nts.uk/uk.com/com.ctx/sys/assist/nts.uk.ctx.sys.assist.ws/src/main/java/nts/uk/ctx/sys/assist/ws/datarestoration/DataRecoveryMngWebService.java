package nts.uk.ctx.sys.assist.ws.datarestoration;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.sys.assist.app.find.datarestoration.DataRecoveryMngDto;
import nts.uk.ctx.sys.assist.app.find.datarestoration.DataRecoveryMngFinder;

@Path("ctx/sys/assist/datarestoration")
@Produces("application/json")
public class DataRecoveryMngWebService {
	@Inject
	private DataRecoveryMngFinder finder;
	@POST
	@Path("followProsess/{recoveryProcessingId}")
	public DataRecoveryMngDto followProsess(@PathParam("recoveryProcessingId") String recoveryProcessingId) {
		return finder.getDataRecoveryMngById(recoveryProcessingId);
	}
}
