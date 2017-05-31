package nts.uk.ctx.at.record.ws.workplace;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import nts.uk.ctx.at.record.app.find.workplace.WorkPlaceDto;
import nts.uk.ctx.at.record.app.find.workplace.WorkPlaceFinder;

@Path("at/record/workplace")
@Produces("application/json")
public class WorkPlaceWebservice {
	
	@Inject
	private WorkPlaceFinder workPlaceFinder;
	
	@POST
	@Path("findall")
	public List<WorkPlaceDto> getAllWorkPlace() {
		return this.workPlaceFinder.getAllWorkPlace();
	}

	@POST
	@Path("findbycode/{workLocationCD}")
	public void getByCode(@PathParam("workLocationCD") String workLocationCD){
		this.workPlaceFinder.getWorkPlace(workLocationCD);
		
	}

	

}
