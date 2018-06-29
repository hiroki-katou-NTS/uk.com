package nts.uk.ctx.sys.assist.ws.datastoragemng;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.sys.assist.app.command.datastoragemng.DataStorageMngCommand;
import nts.uk.ctx.sys.assist.app.command.datastoragemng.RemoveDataStorageMngCommandHandler;
import nts.uk.ctx.sys.assist.app.command.datastoragemng.UpdateDataStorageMngCommandHandler;
import nts.uk.ctx.sys.assist.app.find.datastoragemng.DataStorageMngDto;
import nts.uk.ctx.sys.assist.app.find.datastoragemng.DataStorageMngFinder;

@Path("ctx/sys/assist/app")
@Produces("application/json")
public class DataStorageMngWebService extends WebService {
	@Inject
	private DataStorageMngFinder dataStorageMngFinder;
	
	@Inject
	private UpdateDataStorageMngCommandHandler updateDataStorageMngCommandHandler;
	
	@Inject
	private RemoveDataStorageMngCommandHandler removeDataStorageMngCommandHandler;
	
	@POST
	@Path("findDataStorageMng/{storeProcessingId}")
	public DataStorageMngDto findDataStorageMng(@PathParam("storeProcessingId") String storeProcessingId) {
		return dataStorageMngFinder.getDataStorageMngById(storeProcessingId);
	}
	
	@POST
	@Path("setInterruptSaving")
	public void setInterruptSaving(DataStorageMngCommand command) {
		this.updateDataStorageMngCommandHandler.handle(command);
	}
	
	@POST
	@Path("deleteDataStorageMng")
	public void deleteDataStorageMng(DataStorageMngCommand command) {
		this.removeDataStorageMngCommandHandler.handle(command);
	}
}
