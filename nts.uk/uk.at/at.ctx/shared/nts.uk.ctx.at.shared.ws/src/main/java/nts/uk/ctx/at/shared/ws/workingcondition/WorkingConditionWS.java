package nts.uk.ctx.at.shared.ws.workingcondition;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.find.workingcondition.WorkingConDto;
import nts.uk.ctx.at.shared.app.find.workingcondition.WorkingConFinder;

/**
 * 
 * @author quytb
 *
 */

@Path("ctx/at/shared/workingcondition")
@Produces("application/json")
public class WorkingConditionWS extends WebService{

	@Inject
	private WorkingConFinder workingConditionFinder;
	
	@POST
	@Path("getList/{employeeId}")
	public List<WorkingConDto> getList(@PathParam("employeeId") String employeeId){
		return workingConditionFinder.getListHist(employeeId);		
	}
}
