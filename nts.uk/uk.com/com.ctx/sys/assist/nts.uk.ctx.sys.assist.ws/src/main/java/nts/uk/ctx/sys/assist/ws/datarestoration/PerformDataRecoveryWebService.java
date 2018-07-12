package nts.uk.ctx.sys.assist.ws.datarestoration;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.sys.assist.app.command.datarestoration.DeleteDataRecoveryCommandHandler;
import nts.uk.ctx.sys.assist.app.command.datarestoration.PerformDataRecoveryCommand;
import nts.uk.ctx.sys.assist.app.command.datarestoration.PerformDataRecoveryCommandHandler;

@Path("ctx/sys/assist/datarestoration")
@Produces("application/json")
public class PerformDataRecoveryWebService {
	@Inject
	private PerformDataRecoveryCommandHandler performDataRecover;
	@Inject
	DeleteDataRecoveryCommandHandler deleteDataRecoveryCommandHandler;
	@POST
	@Path("performDataRecover")
	public void setPerformDataRecovery(PerformDataRecoveryCommand command) {
		this.performDataRecover.handle(command);
	}
	@POST
	@Path("deletePerformDataRecover/{recoveryProcessingId}")
	public void deleteDataRecovery(@PathParam("recoveryProcessingId") String recoveryProcessingId) {
		deleteDataRecoveryCommandHandler.handle(recoveryProcessingId);
	}
}
