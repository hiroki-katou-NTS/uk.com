package nts.uk.ctx.basic.ws.organization.position;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.basic.app.command.position.CreatePositionCommand;
import nts.uk.ctx.basic.app.command.position.CreatePositionCommandHandler;
import nts.uk.ctx.basic.app.command.position.RemovePositionCommand;
import nts.uk.ctx.basic.app.command.position.RemovePositionCommandHandler;
import nts.uk.ctx.basic.app.command.position.UpdatePositionCommand;
import nts.uk.ctx.basic.app.command.position.UpdatePositionCommandHandler;
import nts.uk.ctx.basic.app.find.organization.position.PositionDto;
import nts.uk.ctx.basic.app.find.organization.position.PositionFinder;
import nts.uk.shr.com.context.AppContexts;





@Path("")
@Produces(MediaType.APPLICATION_JSON)
public class PositionWebService extends WebService {

	@Inject
	private PositionFinder positionFinder;

	@Inject
	private CreatePositionCommandHandler createPositionCommandHandler;

	@Inject
	private UpdatePositionCommandHandler updatePositionCommandHandler;

	@Inject
	private RemovePositionCommandHandler removePositionCommandHandler;

	@Path("")
	@POST
	public List<PositionDto> init() {
		return positionFinder.init();
	}

	@Path("")
	@POST
	public void add(CreatePositionCommand command) {
		this.createPositionCommandHandler.handle(command);
	}

	@Path("")
	@POST
	public void update(UpdatePositionCommand command) {
		this.updatePositionCommandHandler.handle(command);
	}

	@Path("")
	@POST
	public void remove(RemovePositionCommand command) {
		this.removePositionCommandHandler.handle(command);
	}
	
	@POST
	@Path("findAllPosition")
	public List<PositionDto> getAllPosition(){
		return this.positionFinder.getAllPosition(AppContexts.user().companyCode());		
	}
}
