package nts.uk.ctx.at.request.ws.application.appforleave;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import lombok.Value;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.find.application.appabsence.AppAbsenceFinder;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.AppAbsenceDto;

@Path("at/request/application/appforleave")
@Produces("application/json")
public class AppForLeaveWebService extends WebService{
	@Inject
	private AppAbsenceFinder appForLeaveFinder;
	
	@POST
	@Path("getAppForLeaveStart")
	public AppAbsenceDto getAppForLeaveStart(Param param) {
		return this.appForLeaveFinder.getAppForLeave(param.getAppDate(),param.getEmployeeID());
	}
	@POST
	@Path("getAllAppForLeave")
	public AppAbsenceDto getAppForLeaveAll(ParamGetALL param) {
		return this.appForLeaveFinder.getAllDisplay(param.getStartAppDate(),param.getEndAppDate(),param.isDisplayHalfDayValue(),param.getEmployeeID(),param.getHolidayType(),param.getAlldayHalfDay());
	}
}

@Value
class Param{
	private String appDate;
	private String employeeID;
}

@Value
class ParamGetALL{
	private String startAppDate;
	private String endAppDate;
	private String employeeID;
	private boolean displayHalfDayValue;
	private Integer holidayType;
	private int alldayHalfDay;
}
