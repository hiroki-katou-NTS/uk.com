/**
 * 
 */
package nts.uk.ctx.bs.person.ws.maintenancelayout;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

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

	
	@POST
	@Path("findAll")
	public List<MaintenanceLayoutDto> getAllMaintenenceLayout() {
		return maintenanceLayoutFinder.getAllLayout();

	}

}
