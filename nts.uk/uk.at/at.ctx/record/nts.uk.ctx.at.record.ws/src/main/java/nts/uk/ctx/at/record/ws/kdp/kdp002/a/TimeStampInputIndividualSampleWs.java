package nts.uk.ctx.at.record.ws.kdp.kdp002.a;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.kdp.kdp002.a.RegisterStampIndividualSampleCommand;
import nts.uk.ctx.at.record.app.command.kdp.kdp002.a.RegisterStampIndividualSampleCommandHandler;
import nts.uk.ctx.at.record.app.command.kdp.kdp002.a.RegisterStampIndividualSampleResult;

@Path("at/record/stamp/management/personal")
@Produces("application/json")
public class TimeStampInputIndividualSampleWs extends WebService {

	@Inject
	private RegisterStampIndividualSampleCommandHandler registerHandler;

	@POST
	@Path("stamp/register")
	public RegisterStampIndividualSampleResult register(RegisterStampIndividualSampleCommand cmd) {
		return this.registerHandler.handle(cmd);
	}
	
}
