package nts.uk.ctx.sys.assist.ws.datarestoration;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.sys.assist.app.command.datarestoration.PerformDataRecoveryCommand;
import nts.uk.ctx.sys.assist.app.command.datarestoration.PerformDataRecoveryCommandHandler;

@Path("ctx/sys/assist/datarestoration")
@Produces("application/json")
public class PerformDataRecoveryWebService {
	@Inject
	private PerformDataRecoveryCommandHandler performDataRecover;

	@POST
	@Path("performDataRecover")
	public String setPerformDataRecovery(PerformDataRecoveryCommand command) {
		this.performDataRecover.handle(command);
		return command.recoveryProcessingId;
	}
}
