package nts.uk.ctx.bs.person.ws.itemclassification;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import find.maintenancelayout.MaintenanceLayoutDto;
import find.maintenancelayout.MaintenanceLayoutFinder;

@Path("ctx/bs/person/itemcls")
@Produces("application/json")
public class ItemClassificationWebServices {

	@Inject
	private MaintenanceLayoutFinder mlayoutFinder;
	

	@POST
	@Path("findById/{layoutId}")
	public MaintenanceLayoutDto getMaintenenceLayoutById(@PathParam("layoutId") String layoutId) {
		return mlayoutFinder.getDetails(layoutId).get();
	}
}
