package nts.uk.ctx.at.request.ws.application.appabsence;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.command.application.appabsence.CreatAppAbsenceMobileCommandHandler;
import nts.uk.ctx.at.request.app.command.application.appabsence.RegisterAppAbsenceMobileCommand;
import nts.uk.ctx.at.request.app.command.application.appabsence.UpdateAppAbsenceMobileCommand;
import nts.uk.ctx.at.request.app.command.application.appabsence.UpdateAppAbsenceMobileCommandHandler;
import nts.uk.ctx.at.request.app.find.application.appabsence.AppAbsenceFinderMobile;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.AbsenceCheckRegisterDto;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.AppAbsenceStartInfoDto;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.AppForLeaveStartOutputDto;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.ChangeDateParamMobile;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.CheckInsertMobileParam;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.DetailInfoParam;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.MaxDaySpecHdDto;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.MaxHolidayDayParamMobile;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.SelectTypeHolidayParam;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.SelectWorkOutputDto;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.SelectWorkTimeHolidayParam;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.SelectWorkTypeHolidayParam;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.StartMobileParam;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;

@Path("at/request/application/appforleave/mobile")
@Produces("application/json")
public class AppForLeaveMobileWebService extends WebService {
	
	@Inject
	private AppAbsenceFinderMobile appAbsenceFinderMobile;
	
	@Inject
	private CreatAppAbsenceMobileCommandHandler creatAppAbsence;
	
	@Inject
	private UpdateAppAbsenceMobileCommandHandler update;
	
	@POST
	@Path("start")
	public AppForLeaveStartOutputDto getAppForLeaveStart(StartMobileParam param) {
		return appAbsenceFinderMobile.start(param);
	}
	
	@POST
	@Path("findChangeAppdate")
	public AppAbsenceStartInfoDto findChangeAppdate(ChangeDateParamMobile param) {
		return appAbsenceFinderMobile.changeAppDate(param);
	}
	
	@POST
	@Path("getMaxHoliDay")
	public MaxDaySpecHdDto getMaxHoliDay(MaxHolidayDayParamMobile param) {
		return appAbsenceFinderMobile.getMaxDaySpecHd(param);
	}
	
	@POST
	@Path("selectTypeHoliday")
	public AppAbsenceStartInfoDto selectTypeHoliday(SelectTypeHolidayParam param) {
		return appAbsenceFinderMobile.selectTypeHoliday(param);
	}
	
	@POST
	@Path("selectWorkType")
	public SelectWorkOutputDto selectWorkType(SelectWorkTypeHolidayParam param) {
		return appAbsenceFinderMobile.selectWorkType(param);
	}
	
	@POST
	@Path("selectWorkTime")
	public AppAbsenceStartInfoDto selectWorkTime(SelectWorkTimeHolidayParam param) {
		return appAbsenceFinderMobile.selectWorkTime(param);
	}
	
	@POST
	@Path("checkBeforeInsert")
	public AbsenceCheckRegisterDto checkBeforeInsert(CheckInsertMobileParam param){
		return appAbsenceFinderMobile.checkBeforeInsert(param);
	}
	
	@POST
	@Path("insert")
	public ProcessResult insert(RegisterAppAbsenceMobileCommand param) {
		return creatAppAbsence.handle(param);
	}
	
	@POST
	@Path("update")
	public ProcessResult update(UpdateAppAbsenceMobileCommand param) {
		return update.handle(param);
	}
	
	@POST
	@Path("getDetailInfo")
	public AppForLeaveStartOutputDto getDetailInfo(DetailInfoParam param) {
		return appAbsenceFinderMobile.getDetailInfo(param);
	}
}
