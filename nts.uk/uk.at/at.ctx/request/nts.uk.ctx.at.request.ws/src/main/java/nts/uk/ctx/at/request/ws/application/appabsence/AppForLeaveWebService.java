package nts.uk.ctx.at.request.ws.application.appabsence;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.command.application.appabsence.CreatAppAbsenceCommand;
import nts.uk.ctx.at.request.app.command.application.appabsence.CreatAppAbsenceCommandHandler;
import nts.uk.ctx.at.request.app.command.application.appabsence.UpdateAppAbsenceCommand;
import nts.uk.ctx.at.request.app.command.application.appabsence.UpdateAppAbsenceCommandHandler;
import nts.uk.ctx.at.request.app.find.application.appabsence.AppAbsenceFinder;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.AbsenceCheckRegisterDto;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.AppAbsenceDetailDto;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.AppAbsenceStartInfoDto;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.ChangeRelationShipDto;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.ParamGetAllAppAbsence;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.ParamInitAppAbsence;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.SpecAbsenceParam;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;
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
	public AppAbsenceStartInfoDto getAppForLeaveStart(AppDispInfoStartupDto appDispInfoStartupDto) {
		return this.appForLeaveFinder.getAppForLeave(appDispInfoStartupDto);
	}
	@POST
	@Path("getAllAppForLeave")
	public AppAbsenceStartInfoDto getAppForLeaveAll(ParamGetAllAppAbsence param) {
		return this.appForLeaveFinder.getAllDisplay(param);
	}
	@POST
	@Path("findChangeAppdate")
	public AppAbsenceStartInfoDto findChangeAppdate(ParamGetAllAppAbsence param) {
		return this.appForLeaveFinder.getChangeAppDate(
				param.getStartAppDate(),
				param.isDisplayHalfDayValue(),
				param.getEmployeeID(),
				param.getWorkTypeCode(),
				param.getHolidayType(),
				param.getAlldayHalfDay(),
				param.getPrePostAtr(),
				param.getAppAbsenceStartInfoDto());
	}
	@POST
	@Path("getChangeAllDayHalfDay")
	public AppAbsenceStartInfoDto getChangeAllDayHalfDay(ParamGetAllAppAbsence param) {
		return this.appForLeaveFinder.getChangeByAllDayOrHalfDay(
				param.getAppAbsenceStartInfoDto(),
				param.isDisplayHalfDayValue(),
				param.getAlldayHalfDay(),
				param.getHolidayType());
	}
	@POST
	@Path("getChangeAllDayHalfDayForDetail")
	public AppAbsenceStartInfoDto getChangeByAllDayOrHalfDayForUIDetail(ParamGetAllAppAbsence param) {
		return this.appForLeaveFinder.getChangeByAllDayOrHalfDayForUIDetail(param);
	}
	@POST
	@Path("findChangeDisplayHalfDay")
	public AppAbsenceStartInfoDto getChangeDisplayHalfDay(ParamGetAllAppAbsence param) {
		return this.appForLeaveFinder.getChangeDisplayHalfDay(
				param.getStartAppDate(),
				param.isDisplayHalfDayValue(),
				param.getEmployeeID(),
				param.getWorkTypeCode(),
				param.getHolidayType(),
				param.getAlldayHalfDay(),
				param.getAppAbsenceStartInfoDto());
	}
	@POST
	@Path("findChangeWorkType")
	public AppAbsenceStartInfoDto getChangeWorkType(ParamGetAllAppAbsence param) {
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
		return this.appForLeaveFinder.getWorkingHours(param.getWorkTimeCode(),param.getWorkTypeCode(),param.getHolidayType(), param.getAppAbsenceStartInfoDto());
	}
	@POST
	@Path("insert")
	public ProcessResult insert(CreatAppAbsenceCommand param) {
		return creatAppAbsence.handle(param);
	}
	@POST
	@Path("getByAppID")
	public AppAbsenceDetailDto getByAppID(String appID) {
		return this.appForLeaveFinder.getByAppID(appID);
	}
	
	@POST
	@Path("update")
	public ProcessResult update(UpdateAppAbsenceCommand command) {
		return this.updateAppAbsence.handle(command);
	}
	
	@POST
	@Path("changeRela")
	public ChangeRelationShipDto changeRelationShip(SpecAbsenceParam specAbsenceParam){
		return appForLeaveFinder.changeRelationShip(specAbsenceParam);
	}
	
	@POST
	@Path("checkBeforeRegister")
	public AbsenceCheckRegisterDto checkBeforeRegister(CreatAppAbsenceCommand param){
		return appForLeaveFinder.checkBeforeRegister(param);
	}
	
	@POST
	@Path("checkBeforeUpdate")
	public AbsenceCheckRegisterDto checkBeforeRegister(UpdateAppAbsenceCommand param){
		return appForLeaveFinder.checkBeforeUpdate(param);
	}
}

