package nts.uk.ctx.at.request.ws.application.overtime;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.command.application.overtime.RegisterCommand;
import nts.uk.ctx.at.request.app.command.application.overtime.RegisterCommandHandler;
import nts.uk.ctx.at.request.app.command.application.overtime.UpdateCommand;
import nts.uk.ctx.at.request.app.command.application.overtime.UpdateCommandHandler;
import nts.uk.ctx.at.request.app.find.application.overtime.AppOvertimeFinder;
import nts.uk.ctx.at.request.app.find.application.overtime.BreakTimeZoneSettingDto;
import nts.uk.ctx.at.request.app.find.application.overtime.DisplayInfoOverTimeDto;
import nts.uk.ctx.at.request.app.find.application.overtime.ParamBreakTime;
import nts.uk.ctx.at.request.app.find.application.overtime.ParamCalculation;
import nts.uk.ctx.at.request.app.find.application.overtime.ParamCheckBeforeRegister;
import nts.uk.ctx.at.request.app.find.application.overtime.ParamDetail;
import nts.uk.ctx.at.request.app.find.application.overtime.ParamOverTimeChangeDate;
import nts.uk.ctx.at.request.app.find.application.overtime.ParamOverTimeStart;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.CheckBeforeOutputDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.DetailOutputDto;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
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
	
	@Inject
	private RegisterCommandHandler registerCommandHandler;
	
	@Inject
	private UpdateCommandHandler updateCommandHandler;
	
	@POST
	@Path("start")
	public DisplayInfoOverTimeDto start(ParamOverTimeStart param) {
		return appOvertimeFinder.start(param);
	}
	
	@POST
	@Path("breakTimes")
	public BreakTimeZoneSettingDto getBreakTime(ParamBreakTime param) {
		return appOvertimeFinder.getBreakTime(param);
	}
	
	@POST
	@Path("changeDate")
	public DisplayInfoOverTimeDto changeDate(ParamOverTimeChangeDate param) {
		return appOvertimeFinder.changeDate(param);
	}
	
	
	@POST
	@Path("calculate")
	public DisplayInfoOverTimeDto calculate(ParamCalculation param) {
		return appOvertimeFinder.calculate(param);
	}
	
	@POST
	@Path("checkBeforeRegister")
	public CheckBeforeOutputDto checkBeforeRegister(ParamCheckBeforeRegister param) {
		return appOvertimeFinder.checkBeforeRegister(param);
	}
	
	@POST
	@Path("register")
	public ProcessResult register(RegisterCommand command) {
		return registerCommandHandler.handle(command);
	}
	
	@POST
	@Path("getDetail")
	public DetailOutputDto getDetail(ParamDetail param) {
		return appOvertimeFinder.getDetail(param);
	}
	
	@POST
	@Path("checkBeforeUpdate")
	public CheckBeforeOutputDto checkBeforeUpdate(ParamCheckBeforeRegister param) {
		return appOvertimeFinder.checkBeforeRegister(param);
	}
	
	@POST
	@Path("update")
	public ProcessResult update(UpdateCommand command) {
		return updateCommandHandler.handle(command);
	}
	
}
