package nts.uk.ctx.at.request.ws.application.holidaywork;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.command.application.holidaywork.HolidayWorkRegisterCommandHandler;
import nts.uk.ctx.at.request.app.command.application.holidaywork.HolidayWorkRegisterMultiCommandHandler;
import nts.uk.ctx.at.request.app.command.application.holidaywork.HolidayWorkUpdateCommandHandler;
import nts.uk.ctx.at.request.app.command.application.holidaywork.RegisterCommand;
import nts.uk.ctx.at.request.app.command.application.holidaywork.RegisterMultiCommand;
import nts.uk.ctx.at.request.app.command.application.holidaywork.UpdateCommand;
import nts.uk.ctx.at.request.app.find.application.holidaywork.AppHolidayWorkFinder;
import nts.uk.ctx.at.request.app.find.application.holidaywork.dto.AppHdWorkDispInfoDto;
import nts.uk.ctx.at.request.app.find.application.holidaywork.dto.AppHolidayWorkParamPC;
import nts.uk.ctx.at.request.app.find.application.holidaywork.dto.CheckBeforeOutputDto;
import nts.uk.ctx.at.request.app.find.application.holidaywork.dto.CheckBeforeOutputMultiDto;
import nts.uk.ctx.at.request.app.find.application.holidaywork.dto.HdWorkDetailOutputDto;
import nts.uk.ctx.at.request.app.find.application.holidaywork.dto.HolidayWorkCalculationResultDto;
import nts.uk.ctx.at.request.app.find.application.holidaywork.dto.ParamCalculationHolidayWork;
import nts.uk.ctx.at.request.app.find.application.holidaywork.dto.ParamCheckBeforeRegister;
import nts.uk.ctx.at.request.app.find.application.holidaywork.dto.ParamCheckBeforeRegisterMulti;
import nts.uk.ctx.at.request.app.find.application.holidaywork.dto.ParamCheckBeforeUpdate;
import nts.uk.ctx.at.request.app.find.application.holidaywork.dto.ParamDeleteHdChange;
import nts.uk.ctx.at.request.app.find.application.holidaywork.dto.ParamHdWorkDetail;
import nts.uk.ctx.at.request.app.find.application.holidaywork.dto.ParamHolidayWorkChangeDate;
import nts.uk.ctx.at.request.app.find.application.holidaywork.dto.ParamHolidayWorkChangeWork;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;

/**
 * Refactor5
 * @author huylq
 *
 */
@Path("at/request/application/holidaywork")
@Produces("application/json")
public class HolidayWorkWebService extends WebService{
	
	@Inject
	private AppHolidayWorkFinder appHolidayWorkFinder;
	
	@Inject
	private HolidayWorkRegisterCommandHandler registerCommandHandler;
	
	@Inject
	private HolidayWorkRegisterMultiCommandHandler registerMultiCommandHandler;
	
	@Inject
	private HolidayWorkUpdateCommandHandler updateCommandHandler;
	
	@POST
	@Path("startNew")
	public AppHdWorkDispInfoDto getStartNew(AppHolidayWorkParamPC param) {
		return appHolidayWorkFinder.getStartNew(param);
	}
	
	@POST
	@Path("calculate")
	public HolidayWorkCalculationResultDto calculate(ParamCalculationHolidayWork param) {
		return appHolidayWorkFinder.calculate(param);
	}
	
	@POST
	@Path("checkBeforeRegister")
	public CheckBeforeOutputDto checkBeforeRegister(ParamCheckBeforeRegister param) {
		return appHolidayWorkFinder.checkBeforeRegister(param);
	}
	
	@POST
	@Path("checkBeforeRegisterMulti")
	public CheckBeforeOutputMultiDto checkBeforeRegisterMulti(ParamCheckBeforeRegisterMulti param) {
		return appHolidayWorkFinder.checkBeforeRegisterMulti(param);
	}
	
	@POST
	@Path("register")
	public ProcessResult register(RegisterCommand param) {
		return registerCommandHandler.handle(param);
	}

	@POST
	@Path("registerMulti")
	public ProcessResult registerMulti(RegisterMultiCommand param) {
		return registerMultiCommandHandler.handle(param);
	}
	
	@POST
	@Path("changeAppDate")
	public AppHdWorkDispInfoDto changeAppDate(ParamHolidayWorkChangeDate param) {
		return appHolidayWorkFinder.changeAppDate(param);
	}
	
	@POST
	@Path("changeWorkHours")
	public AppHdWorkDispInfoDto changeWorkHours(ParamHolidayWorkChangeWork param) {
		return appHolidayWorkFinder.changeWorkHours(param);
	}
	
	@POST
	@Path("selectWork")
	public AppHdWorkDispInfoDto selectWork(ParamHolidayWorkChangeWork param) {
		return appHolidayWorkFinder.selectWork(param);
	}
	
	@POST
	@Path("getDetail")
	public HdWorkDetailOutputDto getDetail(ParamHdWorkDetail param) {
		return appHolidayWorkFinder.getDetail(param);
	}
	
	@POST
	@Path("checkBeforeUpdate")
	public CheckBeforeOutputDto checkBeforeUpdate(ParamCheckBeforeUpdate param) {
		return appHolidayWorkFinder.checkBeforeUpdate(param);
	}
	
	@POST
	@Path("update")
	public ProcessResult update(UpdateCommand param) {
		return updateCommandHandler.handle(param);
	}
	
}


