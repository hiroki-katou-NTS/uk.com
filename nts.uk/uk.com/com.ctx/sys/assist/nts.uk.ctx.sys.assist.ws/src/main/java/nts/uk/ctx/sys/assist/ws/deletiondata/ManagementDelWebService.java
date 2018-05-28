package nts.uk.ctx.sys.assist.ws.deletiondata;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.sys.assist.app.command.deletedata.management.ManagementDelCommand;
import nts.uk.ctx.sys.assist.app.command.deletedata.management.UpdateManagementDelCommandHandler;
import nts.uk.ctx.sys.assist.app.command.deletedata.management.RemoveManagementDelCommandHandler;
import nts.uk.ctx.sys.assist.app.find.deletedata.ManagementDelDto;
import nts.uk.ctx.sys.assist.app.find.deletedata.ManagementDelFinder;

@Path("ctx/sys/assist/app")
@Produces("application/json")
public class ManagementDelWebService extends WebService {
	@Inject
	private ManagementDelFinder managementDelFinder;
	
	@Inject
	private UpdateManagementDelCommandHandler updateManagementDelCommandHandler;
	
	@Inject
	private RemoveManagementDelCommandHandler removeManagementDelCommandHandler;
	
	
	@POST
	@Path("findManagementDel/{delId}")
	public ManagementDelDto findManagementDelById(@PathParam("delId") String delId) {
		return managementDelFinder.findManagementDelById(delId);
	}
	
	@POST
	@Path("setInterruptDeleting")
	public void setInterruptDeleting(ManagementDelCommand command) {
		this.updateManagementDelCommandHandler.handle(command);
	}
	
	@POST
	@Path("deleteManagementDel")
	public void deleteManagementDel(ManagementDelCommand command) {
		this.removeManagementDelCommandHandler.handle(command);
	}
}
