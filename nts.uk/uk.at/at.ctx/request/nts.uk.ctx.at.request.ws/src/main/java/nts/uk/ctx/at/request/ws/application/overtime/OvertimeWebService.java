package nts.uk.ctx.at.request.ws.application.overtime;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.find.overtime.GetOvertime;
import nts.uk.ctx.at.request.app.find.overtime.dto.OverTimeDto;

@Path("at/request/application/overtime")
@Produces("application/json")
public class OvertimeWebService extends WebService{

	@Inject
	private GetOvertime overtimeFinder;
	
	@POST
	@Path("getOvertimeByUI")
	public OverTimeDto getOvertimeByUIType(String url,String appDate,int uiType) {
		return this.overtimeFinder.getOvertimeByUIType(url, appDate, uiType);
	}
}
