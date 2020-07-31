package nts.uk.ctx.at.schedule.ws.schedule.employeeinfo.rank;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.schedule.employeeinfo.rank.InsertRankDivisionCommandHandler;
import nts.uk.ctx.at.schedule.app.command.schedule.employeeinfo.rank.RankDivisionCommand;
import nts.uk.ctx.at.schedule.app.find.schedule.employeeinfo.rank.GetRankEmpFinder;
import nts.uk.ctx.at.schedule.app.find.schedule.employeeinfo.rank.RankDivisionDto;
/**
 * 
 * @author hieult
 *
 */
@Path("ctx/at/schedule/setting/employee/rank")
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeRankWS extends WebService {
	
	@Inject
	private GetRankEmpFinder empFinder;
	
	@Inject
	private InsertRankDivisionCommandHandler rankDivisionHandler;
	
	@POST
	@Path("startPage")
	public RankDivisionDto startPage(List<String> listEmpId) {
		return empFinder.getRankbyPriorityOrder(listEmpId);
	}
	
	@POST
	@Path("regis")
	public void regis(RankDivisionCommand command) {
		this.rankDivisionHandler.handle(command);
	}
	
}
