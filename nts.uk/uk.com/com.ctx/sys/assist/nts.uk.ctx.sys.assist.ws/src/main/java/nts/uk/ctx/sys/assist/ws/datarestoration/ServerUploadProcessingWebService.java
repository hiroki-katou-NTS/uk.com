package nts.uk.ctx.sys.assist.ws.datarestoration;

import java.util.Observer;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.task.AsyncTaskInfo;
import nts.uk.ctx.sys.assist.app.command.datarestoration.SyncServerUploadProcessingCommand;
import nts.uk.ctx.sys.assist.app.command.datarestoration.SyncServerUploadProcessingCommandHandler;
import nts.uk.ctx.sys.assist.app.find.datarestoration.ServerPrepareManagementFinder;
import nts.uk.ctx.sys.assist.dom.storage.DataObservable;
import nts.uk.ctx.sys.assist.dom.storage.ObservableService;
import nts.uk.shr.com.context.AppContexts;

@Path("ctx/sys/assist/datarestoration")
@Produces("application/json")
public class ServerUploadProcessingWebService {

	@Inject
	private SyncServerUploadProcessingCommandHandler serverUploadProcessingCommandHandler;

	@Inject
	private ServerPrepareManagementFinder serverPrepareManagementFinder;

	@Inject
	private ObservableService observableService;

	@POST
	@Path("extractData")
	public ExtractDataDto uploadProcessing(SyncServerUploadProcessingCommand command) {
		String[] storageProcessId = new String[1];
		boolean[] hasChanged = new boolean[1];
		String contractCode = AppContexts.user().contractCode();
		observableService.addObservable(contractCode, new DataObservable());
		Observer o = (observable, id) -> {
			storageProcessId[0] = (String) id;
			observable.deleteObservers();
			observableService.removeObservable(contractCode);
			hasChanged[0] = true;
		};
		DataObservable data = observableService.getObservable(contractCode);
		data.addObserver(o);
		AsyncTaskInfo taskInfo = serverUploadProcessingCommandHandler.handle(command);
		while (!hasChanged[0]) {
			
		}
		return new ExtractDataDto(taskInfo, storageProcessId[0]);
	}

	@POST
	@Path("getServerPrepare")
	public String uploadProcessing(String processingId) {
		return serverPrepareManagementFinder.getServerPrepareManagementById(processingId);
	}
}
