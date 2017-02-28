package nts.uk.ctx.basic.ws.organization.position;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.basic.app.command.organization.position.CreateJobTitleCommand;
import nts.uk.ctx.basic.app.command.organization.position.CreateJobTitleCommandHandler;
import nts.uk.ctx.basic.app.command.organization.position.RemoveJobTitleCommand;
import nts.uk.ctx.basic.app.command.organization.position.RemoveJobTitleCommandHandler;
import nts.uk.ctx.basic.app.command.organization.position.UpdateJobTitleCommand;
import nts.uk.ctx.basic.app.command.organization.position.UpdateJobTitleCommandHandler;
import nts.uk.ctx.basic.app.find.organization.position.JobTitleDto;
import nts.uk.ctx.basic.app.find.organization.position.JobTitleFinder;
import nts.uk.shr.com.context.AppContexts;





@Path("")
@Produces(MediaType.APPLICATION_JSON)
public class PositionWebService extends WebService {

	@Inject
	private JobTitleFinder positionFinder;

	@Inject
	private CreateJobTitleCommandHandler createPositionCommandHandler;

	@Inject
	private UpdateJobTitleCommandHandler updatePositionCommandHandler;

	@Inject
	private RemoveJobTitleCommandHandler removePositionCommandHandler;

	@Path("")
	@POST
	public List<JobTitleDto> init() {
		return positionFinder.init();
	}

	@Path("")
	@POST
	public void add(CreateJobTitleCommand command) {
		this.createPositionCommandHandler.handle(command);
	}

	@Path("")
	@POST
	public void update(UpdateJobTitleCommand command) {
		this.updatePositionCommandHandler.handle(command);
	}

	@Path("")
	@POST
	public void remove(RemoveJobTitleCommand command) {
		this.removePositionCommandHandler.handle(command);
	}
	

}
