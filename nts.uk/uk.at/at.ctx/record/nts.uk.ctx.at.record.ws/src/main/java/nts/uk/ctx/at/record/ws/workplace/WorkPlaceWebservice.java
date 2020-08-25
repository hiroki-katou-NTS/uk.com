package nts.uk.ctx.at.record.ws.workplace;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.record.app.find.worklocation.Kcp012WorkplaceListDto;
import nts.uk.ctx.at.record.app.find.worklocation.WorkLocationDto;
import nts.uk.ctx.at.record.app.find.worklocation.WorkLocationFinder;

@Path("at/record/worklocation")
@Produces("application/json")
public class WorkPlaceWebservice {
	
	@Inject
	private WorkLocationFinder workPlaceFinder;
	
	@POST
	@Path("findall")
	public List<WorkLocationDto> getAllWorkPlace() {
		return this.workPlaceFinder.getAllWorkPlace();
	}

	@POST
	@Path("findbycode/{workLocationCD}")
	public void getByCode(@PathParam("workLocationCD") String workLocationCD){
		this.workPlaceFinder.getWorkPlace(workLocationCD);
		
	}
	
	@POST
	@Path("kcp012")
	public List<Kcp012WorkplaceListDto> findAllWorkplace() {
		return this.workPlaceFinder.findWorkplaceList();
	}

	

}
