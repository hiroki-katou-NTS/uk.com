package nts.uk.ctx.at.request.ws.application.holidaywork;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.request.app.command.application.holidaywork.HolidayWorkRegisterMobileCommandHandler;
import nts.uk.ctx.at.request.app.command.application.holidaywork.RegisterMobileCommand;
import nts.uk.ctx.at.request.app.find.application.holidaywork.AppHolidayWorkFinder;
import nts.uk.ctx.at.request.app.find.application.holidaywork.dto.AppHdWorkDispInfoDto;
import nts.uk.ctx.at.request.app.find.application.holidaywork.dto.AppHdWorkDispInfoDtoMobile;
import nts.uk.ctx.at.request.app.find.application.holidaywork.dto.AppHolidayWorkParamMobile;
import nts.uk.ctx.at.request.app.find.application.holidaywork.dto.CheckBeforeOutputDto;
import nts.uk.ctx.at.request.app.find.application.holidaywork.dto.HdWorkDetailOutputDto;
import nts.uk.ctx.at.request.app.find.application.holidaywork.dto.ParamCalculationHolidayWorkMobile;
import nts.uk.ctx.at.request.app.find.application.holidaywork.dto.ParamCheckBeforeRegisterMobile;
import nts.uk.ctx.at.request.app.find.application.holidaywork.dto.ParamHdWorkDetail;
import nts.uk.ctx.at.request.app.find.application.holidaywork.dto.ParamHolidayWorkChangeDateMobile;
import nts.uk.ctx.at.request.app.find.application.holidaywork.dto.ParamHolidayWorkChangeWorkMobile;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;

/**
 * Refactor5
 * @author huylq
 *
 */
@Path("at/request/application/holidaywork/mobile")
@Produces("application/json")
public class HolidayWorkMobileService {

	@Inject
	private AppHolidayWorkFinder appHolidayWorkFinder;
	
	@Inject
	private HolidayWorkRegisterMobileCommandHandler registerMobileCommandHandler;
	
	@POST
	@Path("start")
	public AppHdWorkDispInfoDtoMobile start(AppHolidayWorkParamMobile param) {
		return appHolidayWorkFinder.getStartMobile(param);
	}
	
	@POST
	@Path("changeDate")
	public AppHdWorkDispInfoDto changeDate(ParamHolidayWorkChangeDateMobile param) {
		return appHolidayWorkFinder.changeDateMobile(param);
	}
	
	@POST
	@Path("selectWork")
	public AppHdWorkDispInfoDto selectWork(ParamHolidayWorkChangeWorkMobile param) {
		return appHolidayWorkFinder.selectWorkMobile(param);
	}
	
	@POST
	@Path("changeWorkHours")
	public AppHdWorkDispInfoDto changeWorkHours(ParamHolidayWorkChangeWorkMobile param) {
		return appHolidayWorkFinder.changeWorkHoursMobile(param);
	}
	
	@POST
	@Path("calculate")
	public AppHdWorkDispInfoDto calculate(ParamCalculationHolidayWorkMobile param) {
		return appHolidayWorkFinder.calculateMobile(param);
	}
	
	@POST
	@Path("checkBeforeRegister")
	public CheckBeforeOutputDto checkBeforeInsert(ParamCheckBeforeRegisterMobile param) {
		return appHolidayWorkFinder.checkBeforeRegisterMobile(param);
	}
	
	@POST
	@Path("register")
	public ProcessResult register(RegisterMobileCommand command) {
		return registerMobileCommandHandler.handle(command);
	}
	
	@POST
	@Path("getDetail")
	public HdWorkDetailOutputDto getDetail(ParamHdWorkDetail param) {
		return appHolidayWorkFinder.getDetail(param);
	}
}
