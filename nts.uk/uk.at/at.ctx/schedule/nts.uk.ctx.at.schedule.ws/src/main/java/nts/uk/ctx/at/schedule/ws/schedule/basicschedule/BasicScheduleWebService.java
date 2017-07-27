package nts.uk.ctx.at.schedule.ws.schedule.basicschedule;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.command.JavaTypeResult;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.schedule.basicschedule.RegisterBasicScheduleCommand;
import nts.uk.ctx.at.schedule.app.command.schedule.basicschedule.RegisterBasicScheduleCommandHandler;

/**
 * 
 * @author sonnh1
 *
 */
@Path("at/schedule/basicschedule")
@Produces("application/json")
public class BasicScheduleWebService extends WebService {

	@Inject
	private RegisterBasicScheduleCommandHandler registerBScheduleCommandHandler;


	@POST
	@Path("register")
	public JavaTypeResult<List<String>> register(List<RegisterBasicScheduleCommand> command) {
		return new JavaTypeResult<List<String>>(this.registerBScheduleCommandHandler.handle(command));
	}
}
