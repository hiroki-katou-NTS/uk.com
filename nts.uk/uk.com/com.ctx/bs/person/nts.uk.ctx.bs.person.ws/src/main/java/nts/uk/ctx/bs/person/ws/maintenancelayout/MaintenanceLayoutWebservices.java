/**
 * 
 */
package nts.uk.ctx.bs.person.ws.maintenancelayout;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import command.maintenancelayout.AddMaintenanceLayoutCommand;
import command.maintenancelayout.AddMaintenanceLayoutCommandHandler;
import command.maintenancelayout.UpdateMaintenanceLayoutCommand;
import command.maintenancelayout.UpdateMaintenanceLayoutCommandHandler;
import find.maintenancelayout.MaintenanceLayoutDto;
import find.maintenancelayout.MaintenanceLayoutFinder;
import nts.uk.ctx.bs.person.dom.person.maintenancelayout.MaintenanceLayoutRepository;

/**
 * @author laitv
 *
 */
@Path("ctx/bs/person/maintenance")
@Produces("application/json")
public class MaintenanceLayoutWebservices {

	@Inject
	private MaintenanceLayoutFinder maintenanceLayoutFinder;

	@Inject
	private AddMaintenanceLayoutCommandHandler addMaintenanceLayout;

	@Inject
	private UpdateMaintenanceLayoutCommandHandler updateHandler;

	@POST
	@Path("findAll")
	public List<MaintenanceLayoutDto> getAllMaintenenceLayout() {
		return maintenanceLayoutFinder.getAllLayout();
	}

	@POST
	@Path("coppy")
	public void coppyMaintenenceLayout(UpdateMaintenanceLayoutCommand command) {
		this.updateHandler.handle(command);
	}

	/**
	 * add Maintenance Layout
	 * 
	 * @param command
	 */
	@POST
	@Path("add")
	public void addMaintenanceLayout(AddMaintenanceLayoutCommand command) {
		this.addMaintenanceLayout.handle(command);
	}

	/**
	 * update Maintenance Layout
	 * 
	 * @param command
	 */
	@POST
	@Path("update")
	public void updateMaintenanceLayout() {

	}

	/**
	 * remove Maintenance Layout
	 * 
	 * @param command
	 */
	@POST
	@Path("remove")
	public void removeMaintenanceLayout() {

	}

}
