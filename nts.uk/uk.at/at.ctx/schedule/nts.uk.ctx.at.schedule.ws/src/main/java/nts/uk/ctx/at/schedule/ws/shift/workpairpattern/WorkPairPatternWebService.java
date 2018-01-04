package nts.uk.ctx.at.schedule.ws.shift.workpairpattern;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.shift.workpairpattern.InsertPatternCommand;
import nts.uk.ctx.at.schedule.app.command.shift.workpairpattern.DeletePatternCommand;
import nts.uk.ctx.at.schedule.app.command.shift.workpairpattern.DeleteWorkPairPatternCommandHandler;
import nts.uk.ctx.at.schedule.app.command.shift.workpairpattern.InsertWorkPairPatternCommandHandler;

/**
 * 
 * @author sonnh1
 *
 */
@Path("at/schedule/shift/team/workpairpattern")
@Produces(MediaType.APPLICATION_JSON)
public class WorkPairPatternWebService extends WebService {

	@Inject
	private InsertWorkPairPatternCommandHandler insertWorkPairPatternCommandHandler;

	@Inject
	private DeleteWorkPairPatternCommandHandler deleteWorkPairPatternCommandHandler;

	@POST
	@Path("register")
	public void registerComPattern(InsertPatternCommand command) {
		this.insertWorkPairPatternCommandHandler.handle(command);
	}

	@POST
	@Path("delete")
	public void deletePattern(DeletePatternCommand command) {
		this.deleteWorkPairPatternCommandHandler.handle(command);
	}
}
