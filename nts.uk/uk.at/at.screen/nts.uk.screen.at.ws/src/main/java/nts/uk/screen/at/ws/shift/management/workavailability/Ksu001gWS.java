package nts.uk.screen.at.ws.shift.management.workavailability;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.shift.management.workavailability.Ksu001gScreenQuerry;
import nts.uk.screen.at.app.shift.management.workavailability.WorkAvailabilityInfoDto;

/**
 * 
 * @author quytb
 *
 */

@Path("screen/at/shift/management/workavailability")
@Produces(MediaType.APPLICATION_JSON)
public class Ksu001gWS extends WebService {
	@Inject
	Ksu001gScreenQuerry ksu001gScreenQuerry;

	@POST
	@Path("getAll")
	public List<WorkAvailabilityInfoDto> getWorkAvailability(Ksu001gRequest request) {
		return ksu001gScreenQuerry.getListWorkAvailability(request.getEmployeeIDs(), request.toPeriod());
	}
}
