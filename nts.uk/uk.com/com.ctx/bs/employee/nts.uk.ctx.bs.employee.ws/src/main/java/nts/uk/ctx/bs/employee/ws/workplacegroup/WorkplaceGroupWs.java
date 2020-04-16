package nts.uk.ctx.bs.employee.ws.workplacegroup;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.bs.employee.app.find.employeeinfo.workplacegroup.WorkplaceGroupDto;
import nts.uk.ctx.bs.employee.app.find.employeeinfo.workplacegroup.WorkplaceGroupFinder;

/**
 * @author anhdt
 *
 */
@Path("at/schedule/employeeinfo/workplacegroup")
@Produces(MediaType.APPLICATION_JSON)
public class WorkplaceGroupWs extends WebService {

	@Inject
	private WorkplaceGroupFinder wkpGroupFinder;

	/**
	 * Get all team setting
	 * 
	 * @return list
	 */
	@POST
	@Path("getAll")
	public WorkplaceGroupDto getListRank() {
		return wkpGroupFinder.getWorkplaceGroup();
	}

}

