package nts.uk.ctx.sys.assist.ws.datarestoration;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.sys.assist.app.command.datarestoration.ObtainRecoveryInfoCommand;
import nts.uk.ctx.sys.assist.app.command.datarestoration.ObtainRecoveryInfoCommandHandler;
import nts.uk.ctx.sys.assist.app.command.datarestoration.ServerZipfileValidateStatusDto;
@Path("ctx/sys/assist/datarestoration")
@Produces("application/json")
public class ObtainRecoveryInfoWebService {
	@Inject
	private ObtainRecoveryInfoCommandHandler obtainRecoveryInfo;

	@POST
	@Path("obtainRecovery")
	public ServerZipfileValidateStatusDto ObtainRecoveryInfo(ObtainRecoveryInfoCommand command) {
		return this.obtainRecoveryInfo.handle(command);
	}
}
