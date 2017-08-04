/**
 * 
 */
package nts.uk.ctx.bs.person.ws.maintenancelayout;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import command.maintenancelayout.MaintenanceLayoutCommand;
import command.maintenancelayout.MaintenanceLayoutCommandHandler;
import find.maintenancelayout.MaintenanceLayoutDto;
import find.maintenancelayout.MaintenanceLayoutFinder;

/**
 * @author laitv
 *
 */
@Path("ctx/bs/person/maintenance")
@Produces("application/json")
public class MaintenanceLayoutWebservices {

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
	@Path("saveLayout")
	public void addMaintenanceLayout(MaintenanceLayoutCommand command) {
		this.commandHandler.handle(command);
	}

}
