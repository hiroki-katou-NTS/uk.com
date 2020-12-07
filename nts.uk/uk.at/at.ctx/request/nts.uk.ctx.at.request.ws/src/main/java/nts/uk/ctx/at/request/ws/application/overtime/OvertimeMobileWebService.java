package nts.uk.ctx.at.request.ws.application.overtime;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.command.application.overtime.InsertCommand;
import nts.uk.ctx.at.request.app.command.application.overtime.InsertMobileCommandHandler;
import nts.uk.ctx.at.request.app.find.application.overtime.AppOvertimeFinder;
import nts.uk.ctx.at.request.app.find.application.overtime.BreakTimeZoneSettingDto;
import nts.uk.ctx.at.request.app.find.application.overtime.DisplayInfoOverTimeDto;
import nts.uk.ctx.at.request.app.find.application.overtime.DisplayInfoOverTimeMobileDto;
import nts.uk.ctx.at.request.app.find.application.overtime.ParamBreakTime;
import nts.uk.ctx.at.request.app.find.application.overtime.ParamChangeDateMobile;
import nts.uk.ctx.at.request.app.find.application.overtime.ParamCheckBeforeRegister;
import nts.uk.ctx.at.request.app.find.application.overtime.ParamSelectWorkMobile;
import nts.uk.ctx.at.request.app.find.application.overtime.ParamStartMobile;
import nts.uk.ctx.at.request.app.find.application.overtime.SelectWorkOutputDto;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.ConfirmMsgOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;

@Path("at/request/application/overtime/mobile")
@Produces("application/json")
public class OvertimeMobileWebService extends WebService {
	
	@Inject
	private AppOvertimeFinder appOvertimeFinder;
	
	@Inject
	private InsertMobileCommandHandler insertMobileCommandHandler;
	
	@POST
	@Path("start")
	public DisplayInfoOverTimeMobileDto start(ParamStartMobile param) {
		return appOvertimeFinder.startMobile(param);
	}
	
	@POST
	@Path("changeDate")
	public DisplayInfoOverTimeDto changeDate(ParamChangeDateMobile param) {
		return appOvertimeFinder.changeDateMobile(param);
	}
	
	@POST
	@Path("breakTimes")
	public BreakTimeZoneSettingDto getBreakTime(ParamBreakTime param) {
		return appOvertimeFinder.getBreakTime(param);
	}
	
	@POST
	@Path("selectWorkInfo")
	public SelectWorkOutputDto selectWorkInfo(ParamSelectWorkMobile param) {
		return appOvertimeFinder.selectWorkInfoMobile(param);
	}
	
	@POST
	@Path("checkBeforeInsert")
	public List<ConfirmMsgOutput> checkBeforeInsert(ParamCheckBeforeRegister param) {
		return appOvertimeFinder.checkBeforeInsert(param);
	}
	
	@POST
	@Path("insert")
	public ProcessResult insert(InsertCommand command) {
		return insertMobileCommandHandler.handle(command);
	}
}
