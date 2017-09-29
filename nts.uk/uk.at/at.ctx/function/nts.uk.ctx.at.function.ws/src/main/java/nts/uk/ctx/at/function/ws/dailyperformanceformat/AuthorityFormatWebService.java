package nts.uk.ctx.at.function.ws.dailyperformanceformat;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.function.app.command.dailyperformanceformat.AddAuthorityDailyCommand;
import nts.uk.ctx.at.function.app.command.dailyperformanceformat.AddAuthorityDailyCommandHandler;
import nts.uk.ctx.at.function.app.command.dailyperformanceformat.AddAuthorityMonthlyCommand;
import nts.uk.ctx.at.function.app.command.dailyperformanceformat.AddAuthorityMonthlyCommandHandler;
import nts.uk.ctx.at.function.app.command.dailyperformanceformat.RemoveAuthorityCommand;
import nts.uk.ctx.at.function.app.command.dailyperformanceformat.RemoveAuthorityCommandHandler;
import nts.uk.ctx.at.function.app.command.dailyperformanceformat.UpdateAuthorityDailyCommand;
import nts.uk.ctx.at.function.app.command.dailyperformanceformat.UpdateAuthorityDailyCommandHandler;
import nts.uk.ctx.at.function.app.command.dailyperformanceformat.UpdateAuthorityMonthlyCommand;
import nts.uk.ctx.at.function.app.command.dailyperformanceformat.UpdateAuthorityMonthlyCommandHandler;

@Path("at/function/dailyperformanceformat")
@Produces("application/json")
public class AuthorityFormatWebService extends WebService {
	
	@Inject
	private RemoveAuthorityCommandHandler removeAuthorityCommandHandler;
	
	@Inject
	private AddAuthorityDailyCommandHandler addAuthorityDailyCommandHandler;
	
	@Inject
	private AddAuthorityMonthlyCommandHandler addAuthorityMonthlyCommandHandler;
	
	@Inject
	private UpdateAuthorityMonthlyCommandHandler updateAuthorityMonthlyCommandHandler;
	
	@Inject
	private UpdateAuthorityDailyCommandHandler updateAuthorityDailyCommandHandler;
	
	@POST
	@Path("removeAuthorityFormat")
	public void removeAuthorityFormat(RemoveAuthorityCommand command) {
		this.removeAuthorityCommandHandler.handle(command);
	}
	
	@POST
	@Path("addAuthorityDailyFormat")
	public void addAuthorityDailyFormat(AddAuthorityDailyCommand command) {
		this.addAuthorityDailyCommandHandler.handle(command);
	}
	
	@POST
	@Path("addAuthorityMonthlyFormat")
	public void addAuthorityMonthlyFormat(AddAuthorityMonthlyCommand command) {
		this.addAuthorityMonthlyCommandHandler.handle(command);
	}
	
	@POST
	@Path("updateAuthorityMonthlyFormat")
	public void updateAuthorityMonthlyFormat(UpdateAuthorityMonthlyCommand command) {
		this.updateAuthorityMonthlyCommandHandler.handle(command);
	}
	
	@POST
	@Path("updateAuthorityDailyFormat")
	public void updateAuthorityDailyFormat(UpdateAuthorityDailyCommand command) {
		this.updateAuthorityDailyCommandHandler.handle(command);
	}
}
