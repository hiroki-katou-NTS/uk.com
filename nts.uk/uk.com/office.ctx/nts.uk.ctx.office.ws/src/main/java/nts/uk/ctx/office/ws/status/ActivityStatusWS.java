package nts.uk.ctx.office.ws.status;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.office.app.command.status.ActivityStatusInsertCommandHandler;
import nts.uk.ctx.office.app.command.status.ActivityStatusUpdateCommandHandler;

@Path("ctx/office/status")
@Produces("application/json")
public class ActivityStatusWS {

	@Inject
	private ActivityStatusUpdateCommandHandler activityStatusUpdateCommandHandler;
	
	@Inject
	private ActivityStatusInsertCommandHandler activityStatusInsertCommandHandler;
	
	
}
