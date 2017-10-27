/**
 * 
 */
package nts.uk.ctx.bs.person.ws.layout;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import command.layout.MaintenanceLayoutCommand;
import command.layout.MaintenanceLayoutCommandHandler;
import find.layout.MaintenanceLayoutDto;
import find.layout.MaintenanceLayoutFinder;
import nts.arc.layer.ws.WebService;

@Path("ctx/bs/person/maintenance")
@Produces("application/json")
public class MaintenanceLayoutWebservices extends WebService {

	@Inject
	private MaintenanceLayoutFinder mlayoutFinder;

	@Inject
	private MaintenanceLayoutCommandHandler commandHandler;

	@POST
	@Path("findAll")
	public List<MaintenanceLayoutDto> getAllMaintenenceLayout() {
		return mlayoutFinder.getAllLayout();
	}

	@POST
	@Path("findOne/{lid}")
	public MaintenanceLayoutDto getLayoutById(@PathParam("lid") String lid) {
		 MaintenanceLayoutDto x = mlayoutFinder.getDetails(lid);
		 return x;

	}

	@POST
	@Path("saveLayout")
	public void addMaintenanceLayout(MaintenanceLayoutCommand command) {
		this.commandHandler.handle(command);
	}
}
