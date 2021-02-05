package nts.uk.ctx.at.function.ws.processexecution;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.function.app.command.processexecution.ProcessStartupCommand;
import nts.uk.ctx.at.function.app.find.processexecution.ProcessStartupFinder;
import nts.uk.ctx.at.function.app.find.processexecution.dto.ProcessStartupDto;

@Path("ctx/at/record/approveDataError")
@Produces(MediaType.APPLICATION_JSON)
public class ApprovalDataErrorListWebService extends WebService {
	
	@Inject
	private ProcessStartupFinder processStartupFinder;

	@POST
	@Path("/init")
	public ProcessStartupDto init(ProcessStartupCommand command) {
		return processStartupFinder.init(command);
	}
}
