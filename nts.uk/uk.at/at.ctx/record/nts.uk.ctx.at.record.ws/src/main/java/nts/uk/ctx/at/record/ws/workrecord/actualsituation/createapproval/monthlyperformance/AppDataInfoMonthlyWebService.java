package nts.uk.ctx.at.record.ws.workrecord.actualsituation.createapproval.monthlyperformance;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.find.workrecord.actualsituation.createapproval.monthlyperformance.AppDataInfoMonthlyDto;
import nts.uk.ctx.at.record.app.find.workrecord.actualsituation.createapproval.monthlyperformance.AppDataInfoMonthlyFinder;

@Path("ctx/at/record/workrecord/appdatainfomonthly/")
@Produces(MediaType.APPLICATION_JSON)
public class AppDataInfoMonthlyWebService extends WebService {

	@Inject
	private AppDataInfoMonthlyFinder finder;
	
	@POST
	@Path("findallbyexecid/{executionId}")
	public List< AppDataInfoMonthlyDto> findAllByExecId(@PathParam("executionId") String executionId) {
		return this.finder.getListAppDataInfoMonthlyByExecID(executionId);
	}
}
