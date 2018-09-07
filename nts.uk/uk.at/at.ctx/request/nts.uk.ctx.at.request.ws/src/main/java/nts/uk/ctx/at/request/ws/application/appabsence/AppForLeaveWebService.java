package nts.uk.ctx.at.request.ws.application.appabsence;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.command.application.appabsence.CreatAppAbsenceCommand;
import nts.uk.ctx.at.request.app.command.application.appabsence.CreatAppAbsenceCommandHandler;
import nts.uk.ctx.at.request.app.command.application.appabsence.ParamCheckRegister;
import nts.uk.ctx.at.request.app.command.application.appabsence.UpdateAppAbsenceCommand;
import nts.uk.ctx.at.request.app.command.application.appabsence.UpdateAppAbsenceCommandHandler;
import nts.uk.ctx.at.request.app.find.application.appabsence.AppAbsenceFinder;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.AppAbsenceDto;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.ChangeRelationShipDto;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.ParamGetAllAppAbsence;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.ParamInitAppAbsence;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.dto.TimeZoneUseDto;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;

@Path("at/request/application/appforleave")
@Produces("application/json")
public class AppForLeaveWebService extends WebService{
	@Inject
	private AppAbsenceFinder appForLeaveFinder;
	@Inject
	private CreatAppAbsenceCommandHandler creatAppAbsence;
	@Inject
	private UpdateAppAbsenceCommandHandler updateAppAbsence;
	
	@POST
	@Path("getAppForLeaveStart")
	public AppAbsenceDto getAppForLeaveStart(ParamInitAppAbsence param) {
		return this.appForLeaveFinder.getAppForLeave(param.getAppDate(),param.getEmployeeID(),param.getEmployeeIDs());
	}
	@POST
	@Path("getAllAppForLeave")
	public AppAbsenceDto getAppForLeaveAll(ParamGetAllAppAbsence param) {
		return this.appForLeaveFinder.getAllDisplay(param);
	}
	@POST
	@Path("findChangeAppdate")
	public AppAbsenceDto findChangeAppdate(ParamGetAllAppAbsence param) {
		return this.appForLeaveFinder.getChangeAppDate(param.getStartAppDate(),param.isDisplayHalfDayValue(),param.getEmployeeID(),param.getWorkTypeCode(),param.getHolidayType(),param.getAlldayHalfDay(),param.getPrePostAtr());
	}
	@POST
	@Path("getChangeAllDayHalfDay")
	public AppAbsenceDto getChangeAllDayHalfDay(ParamGetAllAppAbsence param) {
		return this.appForLeaveFinder.getChangeByAllDayOrHalfDay(param.getStartAppDate(),param.isDisplayHalfDayValue(),param.getEmployeeID(),param.getHolidayType(),param.getAlldayHalfDay());
	}
	@POST
	@Path("getChangeAllDayHalfDayForDetail")
	public AppAbsenceDto getChangeByAllDayOrHalfDayForUIDetail(ParamGetAllAppAbsence param) {
		return this.appForLeaveFinder.getChangeByAllDayOrHalfDayForUIDetail(param.getStartAppDate(),param.isDisplayHalfDayValue(),param.getEmployeeID(),param.getHolidayType(),param.getAlldayHalfDay());
	}
	@POST
	@Path("findChangeDisplayHalfDay")
	public AppAbsenceDto getChangeDisplayHalfDay(ParamGetAllAppAbsence param) {
		return this.appForLeaveFinder.getChangeDisplayHalfDay(param.getStartAppDate(),param.isDisplayHalfDayValue(),param.getEmployeeID(),param.getWorkTypeCode(),param.getHolidayType(),param.getAlldayHalfDay());
	}
	@POST
	@Path("findChangeWorkType")
	public AppAbsenceDto getChangeWorkType(ParamGetAllAppAbsence param) {
		return this.appForLeaveFinder.getChangeWorkType(param);
	}
	@POST
	@Path("getListWorkTime")
	public List<String> getListWorkTime(ParamGetAllAppAbsence param) {
		return this.appForLeaveFinder.getListWorkTimeCodes(param.getStartAppDate(),param.getEmployeeID());
	}
	@POST
	@Path("getWorkingHours")
	public List<TimeZoneUseDto> getWorkingHours(ParamGetAllAppAbsence param) {
		return this.appForLeaveFinder.getWorkingHours(param.getWorkTimeCode(),param.getWorkTypeCode(),param.getHolidayType());
	}
	@POST
	@Path("insert")
	public ProcessResult insert(CreatAppAbsenceCommand param) {
		return creatAppAbsence.handle(param);
	}
	@POST
	@Path("getByAppID")
	public AppAbsenceDto getByAppID(String appID) {
		return this.appForLeaveFinder.getByAppID(appID);
	}
	
	@POST
	@Path("update")
	public ProcessResult update(UpdateAppAbsenceCommand command) {
		return this.updateAppAbsence.handle(command);
	}
	
	@POST
	@Path("changeRela/{workTypeCD}/{relationCD}")
	public ChangeRelationShipDto changeRelationShip(@PathParam("workTypeCD") String workTypeCD, @PathParam("relationCD") String relationCD){
		return appForLeaveFinder.changeRelationShip(workTypeCD, relationCD);
	}
	
	@POST
	@Path("checkRegister")
	public void checkRegister(ParamCheckRegister param){
		creatAppAbsence.checkRegister(param);
	}
}

