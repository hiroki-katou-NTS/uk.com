package nts.uk.ctx.at.request.ws.application.appabsence;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.find.application.appabsence.AppAbsenceFinderMobile;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.AppAbsenceStartInfoDto;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.AppForLeaveStartOutputDto;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.ChangeDateParamMobile;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.MaxDaySpecHdDto;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.MaxHolidayDayParamMobile;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.SelectTypeHolidayParam;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.SelectWorkOutputDto;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.SelectWorkTypeHolidayParam;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.StartMobileParam;

@Path("at/request/application/appforleave/mobile")
@Produces("application/json")
public class AppForLeaveMobileWebService extends WebService {
	
	@Inject
	private AppAbsenceFinderMobile appAbsenceFinderMobile;
	
	
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
	
	

}
