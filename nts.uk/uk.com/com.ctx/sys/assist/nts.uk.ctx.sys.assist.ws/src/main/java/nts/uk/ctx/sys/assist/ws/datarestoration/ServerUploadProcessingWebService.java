package nts.uk.ctx.sys.assist.ws.datarestoration;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.sys.assist.app.command.datarestoration.SyncServerUploadProcessingCommand;
import nts.uk.ctx.sys.assist.app.command.datarestoration.SyncServerUploadProcessingCommandHandler;
import nts.uk.ctx.sys.assist.app.find.datarestoration.ServerPrepareManagementFinder;

@Path("ctx/sys/assist/datarestoration")
@Produces("application/json")
public class ServerUploadProcessingWebService {
	
	@Inject
	private SyncServerUploadProcessingCommandHandler serverUploadProcessingCommandHandler;
	@Inject
	private ServerPrepareManagementFinder serverPrepareManagementFinder;
	@POST
	@Path("extractData")
	public void uploadProcessing(SyncServerUploadProcessingCommand command) {
		serverUploadProcessingCommandHandler.handle(command);
		return;
	}
	
	@POST
	@Path("getServerPrepare")
	public String uploadProcessing(String processingId) {
		return serverPrepareManagementFinder.getServerPrepareManagementById(processingId);
	}
}
