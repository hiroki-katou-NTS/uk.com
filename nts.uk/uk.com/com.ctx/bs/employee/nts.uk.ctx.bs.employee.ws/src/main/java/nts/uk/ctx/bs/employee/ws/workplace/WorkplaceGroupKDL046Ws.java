package nts.uk.ctx.bs.employee.ws.workplace;

import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.bs.employee.dom.workplace.group.AffWorkplaceGroupRespository;
import nts.uk.ctx.bs.employee.dom.workplace.group.WorkplaceGroup;
import nts.uk.shr.com.context.AppContexts;

@Path("bs/employee/kdl046")
@Produces("application/json")
public class WorkplaceGroupKDL046Ws extends WebService {
	
	@Inject
	private AffWorkplaceGroupRespository repo;
	
	@POST
	@Path("getData")
	public WorkplaceInforDto getAllActiveWorkplace(String workplaceID) {
		String companyId = AppContexts.user().companyId();
		WorkplaceInforDto result = null;
	
		Optional<WorkplaceGroup> optWorkplaceGroup = repo.getWorkplaceGroup(companyId, workplaceID);
		if (optWorkplaceGroup.isPresent()) {
			 result = new WorkplaceInforDto(
					 optWorkplaceGroup.get().getWKPGRPID(),
					 optWorkplaceGroup.get().getWKPGRPCode().v(),
					 optWorkplaceGroup.get().getWKPGRPName().v(),
					 true);
		} else {
			 result = new WorkplaceInforDto("", "", "", false);
		}
		
		return result;
	}
}
