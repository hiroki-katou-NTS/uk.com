package nts.uk.ctx.at.request.ws.application.appabsence;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import lombok.Value;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.command.application.appabsence.CreatAppAbsenceCommand;
import nts.uk.ctx.at.request.app.command.application.appabsence.CreatAppAbsenceCommandHandler;
import nts.uk.ctx.at.request.app.command.application.appabsence.UpdateAppAbsenceCommand;
import nts.uk.ctx.at.request.app.command.application.appabsence.UpdateAppAbsenceCommandHandler;
import nts.uk.ctx.at.request.app.find.application.appabsence.AppAbsenceFinder;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.AppAbsenceDto;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;

@Path("at/request/application/appforleave")
@Produces("application/json")
public class AppForLeaveWebService extends WebService{
	@Inject
	private AppAbsenceFinder appForLeaveFinder;
	@Inject
	private CreatAppAbsenceCommandHandler creatAppAbsenceCommandHandler;
	@Inject
	private UpdateAppAbsenceCommandHandler updateAppAbsenceCommandHandler;
	
	@POST
	@Path("getAppForLeaveStart")
	public AppAbsenceDto getAppForLeaveStart(Param param) {
		return this.appForLeaveFinder.getAppForLeave(param.getAppDate(),param.getEmployeeID(),param.getEmployeeIDs());
	}
	@POST
	@Path("getAllAppForLeave")
	public AppAbsenceDto getAppForLeaveAll(ParamGetALL param) {
		return this.appForLeaveFinder.getAllDisplay(param.getStartAppDate(),param.isDisplayHalfDayValue(),param.getEmployeeID(),param.getHolidayType(),param.getAlldayHalfDay());
	}
	@POST
	@Path("findChangeAppdate")
	public AppAbsenceDto findChangeAppdate(ParamGetALL param) {
		return this.appForLeaveFinder.getChangeAppDate(param.getStartAppDate(),param.isDisplayHalfDayValue(),param.getEmployeeID(),param.getWorkTypeCode(),param.getHolidayType(),param.getAlldayHalfDay(),param.getPrePostAtr());
	}
	@POST
	@Path("getChangeAllDayHalfDay")
	public AppAbsenceDto getChangeAllDayHalfDay(ParamGetALL param) {
		return this.appForLeaveFinder.getChangeByAllDayOrHalfDay(param.getStartAppDate(),param.isDisplayHalfDayValue(),param.getEmployeeID(),param.getHolidayType(),param.getAlldayHalfDay());
	}
	@POST
	@Path("getChangeAllDayHalfDayForDetail")
	public AppAbsenceDto getChangeByAllDayOrHalfDayForUIDetail(ParamGetALL param) {
		return this.appForLeaveFinder.getChangeByAllDayOrHalfDayForUIDetail(param.getStartAppDate(),param.isDisplayHalfDayValue(),param.getEmployeeID(),param.getHolidayType(),param.getAlldayHalfDay());
	}
	@POST
	@Path("findChangeDisplayHalfDay")
	public AppAbsenceDto getChangeDisplayHalfDay(ParamGetALL param) {
		return this.appForLeaveFinder.getChangeDisplayHalfDay(param.getStartAppDate(),param.isDisplayHalfDayValue(),param.getEmployeeID(),param.getWorkTypeCode(),param.getHolidayType(),param.getAlldayHalfDay());
	}
	@POST
	@Path("findChangeWorkType")
	public AppAbsenceDto getChangeWorkType(ParamGetALL param) {
		return this.appForLeaveFinder.getChangeWorkType(param.getStartAppDate(),param.getEmployeeID(),param.getWorkTypeCode(),param.getHolidayType(),param.getWorkTimeCode());
	}
	@POST
	@Path("getListWorkTime")
	public List<String> getListWorkTime(ParamGetALL param) {
		return this.appForLeaveFinder.getListWorkTimeCodes(param.getStartAppDate(),param.getEmployeeID());
	}
	@POST
	@Path("getWorkingHours")
	public AppAbsenceDto getWorkingHours(ParamGetALL param) {
		return this.appForLeaveFinder.getWorkingHours(param.getWorkTimeCode(),param.getWorkTypeCode(),param.getHolidayType());
	}
	@POST
	@Path("insert")
	public ProcessResult insert(CreatAppAbsenceCommand param) {
		return creatAppAbsenceCommandHandler.handle(param);
	}
	@POST
	@Path("getByAppID")
	public AppAbsenceDto getByAppID(String appID) {
		return this.appForLeaveFinder.getByAppID(appID);
	}
	
	@POST
	@Path("update")
	public ProcessResult update(UpdateAppAbsenceCommand command) {
		return this.updateAppAbsenceCommandHandler.handle(command);
	}
	
}

@Value
class Param{
	private String appDate;
	private String employeeID;
	private List<String> employeeIDs;
}

@Value
class ParamGetALL{
	private String startAppDate;
	private String endAppDate;
	private String employeeID;
	private boolean displayHalfDayValue;
	private Integer holidayType;
	private int alldayHalfDay;
	private String workTypeCode;
	private int prePostAtr;
	private String workTimeCode;
}
