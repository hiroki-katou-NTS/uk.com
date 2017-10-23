package nts.uk.ctx.at.record.ws.log;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.find.log.EmpCalAndSumExeLogFinder;

@Path("at/record/log")
@Produces("application/json")
public class EmpCalAndSumExeLogWebService extends WebService {
	
	@Inject
	private EmpCalAndSumExeLogFinder empCalAndSumExeLogFinder;
	
//	@POST
//	@Path("getstampbyempcode/{startDate}/{endDate}")
	
}
