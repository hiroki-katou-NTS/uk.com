package nts.uk.ctx.at.request.ws.application.lateorleaveearly;


import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.command.application.lateorleaveearly.CreateLateOrLeaveEarlyCommand;
import nts.uk.ctx.at.request.app.command.application.lateorleaveearly.CreateLateOrLeaveEarlyCommandHandler;
import nts.uk.ctx.at.request.app.command.application.lateorleaveearly.DeleteLateOrLeaveEarlyCommand;
import nts.uk.ctx.at.request.app.command.application.lateorleaveearly.DeleteLateOrLeaveEarlyCommandHandler;
import nts.uk.ctx.at.request.app.command.application.lateorleaveearly.UpdateLateOrLeaveEarlyCommand;
import nts.uk.ctx.at.request.app.command.application.lateorleaveearly.UpdateLateOrLeaveEarlyCommandHandler;
import nts.uk.ctx.at.request.app.find.application.lateorleaveearly.LateOrLeaveEarlyFinder;
import nts.uk.ctx.at.request.app.find.application.lateorleaveearly.ScreenLateOrLeaveEarlyDto;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;

/**
 * 
 * @author hieult
 *
 */	
@Path("at/request/lateorleaveearly")
@Produces("application/json")
public class LateOrLeaveEarlyWebservice extends WebService{

	@Inject
	private CreateLateOrLeaveEarlyCommandHandler createLateOrLeaveEarly;
	
	@Inject
	private UpdateLateOrLeaveEarlyCommandHandler updateLateOrLeaveEarly;
	
	@Inject
	private DeleteLateOrLeaveEarlyCommandHandler deleteLateOrLeaveEarly;
	
	@Inject
	private LateOrLeaveEarlyFinder finder;
	
	@POST
	@Path("findbycode")
	public ScreenLateOrLeaveEarlyDto getByCode(String appID) {
		ScreenLateOrLeaveEarlyDto data = this.finder.getLateOrLeaveEarly(appID);
		return data;
	}
	
	@POST 
	@Path("create")
	public ProcessResult createLateOrLeaveEarly(CreateLateOrLeaveEarlyCommand command) {
		return this.createLateOrLeaveEarly.handle(command);
	}
	
	@POST 
	@Path("update")
	public ProcessResult updateLateOrLeaveEarly(UpdateLateOrLeaveEarlyCommand command) {
		return this.updateLateOrLeaveEarly.handle(command);
	}
	
	@POST 
	@Path("delete")
	public ProcessResult deleteLateOrLeaveEarly(DeleteLateOrLeaveEarlyCommand command) {
		return this.deleteLateOrLeaveEarly.handle(command);
	}
	
}
