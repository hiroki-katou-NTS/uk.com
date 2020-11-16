package nts.uk.ctx.at.request.ws.application.overtime;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.find.application.overtime.AppOvertimeFinder;
import nts.uk.ctx.at.request.app.find.application.overtime.DisplayInfoOverTimeDto;
import nts.uk.ctx.at.request.app.find.application.overtime.ParamOverTimeChangeDate;
import nts.uk.ctx.at.request.app.find.application.overtime.ParamOverTimeStart;
/**
 * Refactor5 
 * @author hoangnd
 *
 */
@Path("at/request/application/overtime")
@Produces("application/json")
public class OvertimeWebService extends WebService {
	
	@Inject
	private AppOvertimeFinder appOvertimeFinder;
	
	@POST
	@Path("start")
	public DisplayInfoOverTimeDto start(ParamOverTimeStart param) {
		return appOvertimeFinder.start(param);
	}
	
	@POST
	@Path("changeDate")
	public DisplayInfoOverTimeDto changeDate(ParamOverTimeChangeDate param) {
		return appOvertimeFinder.changeDate(param);
	}
}
