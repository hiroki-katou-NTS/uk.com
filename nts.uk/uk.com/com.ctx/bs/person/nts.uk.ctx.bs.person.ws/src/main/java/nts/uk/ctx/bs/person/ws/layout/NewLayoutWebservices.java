package nts.uk.ctx.bs.person.ws.layout;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import command.maintenancelayout.MaintenanceLayoutCommand;
import find.newlayout.NewLayoutDto;
import find.newlayout.NewLayoutFinder;
import nts.arc.layer.ws.WebService;

@Path("ctx/bs/person/maintenance")
@Produces("application/json")
public class NewLayoutWebservices extends WebService {

	@Inject
	private NewLayoutFinder nLayoutFinder;

	@POST
	@Path("getNewLayout")
	public NewLayoutDto getNewLayout() {
		return nLayoutFinder.getLayout();

	}
	
	@POST
	@Path("saveLayout")
	public void addMaintenanceLayout(MaintenanceLayoutCommand command) {
		//this.comsmandHandler.handle(command);
	}
}
