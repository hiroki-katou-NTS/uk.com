/**
 * 
 */
package nts.uk.ctx.bs.person.ws.maintenancelayout;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import command.maintenancelayout.MaintenanceLayoutCommand;
import command.maintenancelayout.MaintenanceLayoutCommandHandler;
import find.maintenancelayout.MaintenanceLayoutDto;
import find.maintenancelayout.MaintenanceLayoutFinder;
import find.roles.auth.category.PersonInfoCategoryDetailDto;
import nts.arc.layer.ws.WebService;


@Path("ctx/bs/person/maintenance")
@Produces("application/json")
public class MaintenanceLayoutWebservices extends WebService{

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
		return mlayoutFinder.getDetails(lid);

	}
	

	@POST
	@Path("saveLayout")
	public void addMaintenanceLayout(MaintenanceLayoutCommand command) {
		this.commandHandler.handle(command);
	}
}
