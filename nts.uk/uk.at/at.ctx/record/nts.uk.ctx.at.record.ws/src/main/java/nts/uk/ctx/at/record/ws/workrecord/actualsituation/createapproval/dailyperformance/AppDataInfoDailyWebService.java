package nts.uk.ctx.at.record.ws.workrecord.actualsituation.createapproval.dailyperformance;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.find.workrecord.actualsituation.createapproval.dailyperformance.AppDataInfoDailyDto;
import nts.uk.ctx.at.record.app.find.workrecord.actualsituation.createapproval.dailyperformance.AppDataInfoDailyFinder;

@Path("ctx/at/record/workrecord/appdatainfodaily/")
@Produces(MediaType.APPLICATION_JSON)
public class AppDataInfoDailyWebService extends WebService {

	@Inject
	private AppDataInfoDailyFinder finder;
	
	@POST
	@Path("findallbyexecid/{executionId}")
	public List< AppDataInfoDailyDto> findAllByExecId(@PathParam("executionId") String executionId) {
		return this.finder.getListAppDataInfoDailyByExecID(executionId);
	}
}
