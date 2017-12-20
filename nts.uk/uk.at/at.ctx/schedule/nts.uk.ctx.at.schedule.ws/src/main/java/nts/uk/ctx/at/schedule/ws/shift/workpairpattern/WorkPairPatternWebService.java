package nts.uk.ctx.at.schedule.ws.shift.workpairpattern;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.find.shift.workpairpattern.WorkPairPatternDto;
import nts.uk.ctx.at.schedule.app.find.shift.workpairpattern.WorkPairPatternFinder;

/**
 * 
 * @author sonnh1
 *
 */
@Path("at/schedule/shift/workpairpattern")
@Produces("application/json")
public class WorkPairPatternWebService extends WebService {
	@Inject
	private WorkPairPatternFinder workPairPatternFinder;

	@POST
	@Path("findAllDataWorkPairPattern")
	public WorkPairPatternDto getDataWorkPairPattern(String workplaceId) {
		return new WorkPairPatternDto(this.workPairPatternFinder.getAllDataComPattern(),
				this.workPairPatternFinder.getAllDataWkpPattern(workplaceId));
	}

}
