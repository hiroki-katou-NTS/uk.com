package nts.uk.ctx.sys.assist.ws.datarestoration;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.assist.app.command.datarestoration.PerformDataRecoveryCommandHandler;
import nts.uk.ctx.sys.assist.app.find.datarestoration.PerformDataRecoveryDto;

@Path("ctx/sys/assist/datarestoration")
@Produces("application/json")
public class PerformDataRecoveryWebService {
	@Inject
	private PerformDataRecoveryCommandHandler performDataRecover;
	
	@POST
	@Path("performDataRecover")
	public void setPerformDataRecovery(CommandHandlerContext<PerformDataRecoveryDto> command) {
		this.performDataRecover.handle(command);
	}
}
