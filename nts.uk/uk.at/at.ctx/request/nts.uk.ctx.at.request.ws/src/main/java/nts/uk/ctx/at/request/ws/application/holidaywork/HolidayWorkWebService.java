package nts.uk.ctx.at.request.ws.application.holidaywork;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import lombok.Value;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.command.application.holidaywork.RegisterCommand;
import nts.uk.ctx.at.request.app.command.application.holidaywork.RegisterMultiCommand;
import nts.uk.ctx.at.request.app.command.application.holidaywork.UpdateCommand;
import nts.uk.ctx.at.request.app.command.application.holidaywork.HolidayWorkRegisterCommandHandler;
import nts.uk.ctx.at.request.app.command.application.holidaywork.HolidayWorkRegisterMultiCommandHandler;
import nts.uk.ctx.at.request.app.command.application.holidaywork.HolidayWorkUpdateCommandHandler;
import nts.uk.ctx.at.request.app.find.application.holidaywork.AppHolidayWorkFinder;
import nts.uk.ctx.at.request.app.find.application.holidaywork.dto.AppHdWorkDispInfoDto;
import nts.uk.ctx.at.request.app.find.application.holidaywork.dto.HolidayWorkCalculationResultDto;
import nts.uk.ctx.at.request.app.find.application.holidaywork.dto.ParamCalculationHolidayWork;
import nts.uk.ctx.at.request.app.find.application.holidaywork.dto.ParamCheckBeforeRegister;
import nts.uk.ctx.at.request.app.find.application.holidaywork.dto.ParamCheckBeforeRegisterMulti;
import nts.uk.ctx.at.request.app.find.application.holidaywork.dto.ParamCheckBeforeUpdate;
import nts.uk.ctx.at.request.app.find.application.holidaywork.dto.ParamDeleteHdChange;
import nts.uk.ctx.at.request.app.find.application.holidaywork.dto.ParamHdWorkDetail;
import nts.uk.ctx.at.request.app.find.application.holidaywork.dto.ParamHolidayWorkChangeDate;
import nts.uk.ctx.at.request.app.find.application.holidaywork.dto.ParamHolidayWorkChangeWork;
import nts.uk.ctx.at.request.app.find.application.holidaywork.dto.AppHolidayWorkParamPC;
import nts.uk.ctx.at.request.app.find.application.holidaywork.dto.CheckBeforeOutputDto;
import nts.uk.ctx.at.request.app.find.application.holidaywork.dto.CheckBeforeOutputMultiDto;
import nts.uk.ctx.at.request.app.find.application.holidaywork.dto.HdWorkDetailOutputDto;
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
	public List<ProcessResult> registerMulti(RegisterMultiCommand param) {
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
	@Path("checkBeforeRegister")
	public CheckBeforeOutputDto checkBeforeUpdate(ParamCheckBeforeUpdate param) {
		return appHolidayWorkFinder.checkBeforeUpdate(param);
	}
	
	@POST
	@Path("register")
	public ProcessResult update(UpdateCommand param) {
		return updateCommandHandler.handle(param);
	}
	
	@POST
	@Path("deleteHdChange")
	public void deleteHdChange(ParamDeleteHdChange param) {
		appHolidayWorkFinder.deleteHdChange(param);
	}
	
	
	
	
	
	
	
	
	
	
	
	
//	@Inject
//	private AppHolidayWorkFinder appHolidayWorkFinder;
//	@Inject
//	private CheckBeforeRegisterHolidayWork checkBeforeRegisterHolidayWork;
//	@Inject
//	private CreateHolidayWorkCommandHandler createHolidayWorkCommandHandler;
//	@Inject
//	private UpdateHolidayWorkCommandHandler updateHolidayWorkCommandHandle;
//	
//	@Inject
//	private OvertimeRestAppCommonSetRepository overTimeSetRepo;
//	
//	@Inject
//	private CommonOvertimeHoliday commonOvertimeHoliday;
//	
//	@POST
//	@Path("getHolidayWorkByUI")
//	public AppHdWorkDispInfoDto getOvertimeByUIType(ParamGetHolidayWork param) {
//		/*AppHolidayWorkDto appHolidayWorkDto = this.appHolidayWorkFinder.getAppHolidayWork(param.getAppDate(), param.getUiType(),param.getLstEmployee(),param.getPayoutType(),param.getEmployeeID(),new AppHolidayWorkDto());
//		session.setAttribute("appHolidayWorkDto", appHolidayWorkDto);
//		return appHolidayWorkDto;*/
//		return appHolidayWorkFinder.getAppHolidayWork(param.getAppDate(), param.getUiType(),param.getLstEmployee(),param.getPayoutType(),param.getEmployeeID(),new AppHolidayWorkDto());
//	}
//	@POST
//	@Path("findChangeAppDate")
//	public AppHdWorkDispInfoDto findChangeAppDate(ParamChangeAppDate param) {
//		/*AppHolidayWorkDto appHolidayWorkDto = (AppHolidayWorkDto) session.getAttribute("appHolidayWorkDto");*/
//		return this.appHolidayWorkFinder.findChangeAppDate(param.getAppDate(), param.getPrePostAtr(),param.getSiftCD(),param.getOvertimeHours(),param.getChangeEmployee(),
//				param.getStartTime(), param.getEndTime(), param.getAppHdWorkDispInfoCmd());
//	}
//	/*@POST
//	@Path("calculationresultConfirm")
//	public ColorConfirmResult calculationresultConfirm(ParamCalculationHolidayWork param){
//		return this.appHolidayWorkFinder.calculationresultConfirm(param.getBreakTimes(),
//															param.getPrePostAtr(),
//															param.getAppDate(),
//															param.getSiftCD(),
//															param.getWorkTypeCode(),
//															param.getEmployeeID(),
//															param.getInputDate() == null ? null :GeneralDateTime.fromString(param.getInputDate(), "yyyy/MM/dd HH:mm"),
//															param.getStartTime(),
//															param.getEndTime(),
//															param.getStartTimeRests(),
//															param.getEndTimeRests());
//	}
//	@POST
//	@Path("getcalculationresult")
//	public List<CaculationTime> getCalculationTime(ParamCalculationHolidayWork param){
//		return this.appHolidayWorkFinder.getCaculationValue(param.getBreakTimes(),
//															param.getPrePostAtr(),
//															param.getAppDate(),
//															param.getSiftCD(),
//															param.getWorkTypeCode(),
//															param.getEmployeeID(),
//															param.getInputDate() == null ? null :GeneralDateTime.fromString(param.getInputDate(), "yyyy/MM/dd HH:mm"),
//															param.getStartTime(),
//															param.getEndTime(),
//															param.getStartTimeRests(),
//															param.getEndTimeRests(),
//															param.getDailyAttendanceTimeCaculationImport());
//	}*/
//	
//	@POST
//	@Path("getCalculateValue")
//	public PreActualColorResult getCalculateValue(ParamCalculateOvertime param) {
//		return appHolidayWorkFinder.getCalculateValue(
//				param.employeeID, 
//				param.appDate, 
//				param.prePostAtr, 
//				param.workTypeCD, 
//				param.workTimeCD, 
//				param.overtimeInputLst, 
//				param.startTime, 
//				param.endTime, 
//				param.getStartTimeRests(), 
//				param.getEndTimeRests());
//	}
//	
//	@POST
//	@Path("create")
//	public ProcessResult createHolidayWork(CreateHolidayWorkCommand command){
//		return createHolidayWorkCommandHandler.handle(command);
//	}
//	/*@POST
//	@Path("beforeRegisterColorConfirm")
//	public ColorConfirmResult beforeRegisterColorConfirm(CreateHolidayWorkCommand command){
//		return checkBeforeRegisterHolidayWork.checkBeforeRregisterColor(command);
//	}*/
//	@POST
//	@Path("checkBeforeRegister")
//	public HdWorkCheckRegisterDto checkBeforeRegister(CreateHolidayWorkCommand command){
//		return checkBeforeRegisterHolidayWork.checkBeforeRegister(command);
//	}
//	@POST
//	@Path("findByAppID")
//	public HolidayWorkDetailDto findByChangeAppID(String appID) {
//		/*AppHolidayWorkDto appHolidayWorkDto = this.appHolidayWorkFinder.getAppHolidayWorkByAppID(appID);
//		session.setAttribute("appHolidayWorkDto", appHolidayWorkDto);
//		return appHolidayWorkDto;*/
//		return appHolidayWorkFinder.getAppHolidayWorkByAppID(appID);
//	}
//	/*@POST
//	@Path("beforeUpdateColorConfirm")
//	public ColorConfirmResult beforeUpdateColorConfirm(CreateHolidayWorkCommand command){
//		return checkBeforeRegisterHolidayWork.checkBeforeUpdateColor(command);
//	}*/
//	@POST
//	@Path("checkBeforeUpdate")
//	public HdWorkCheckRegisterDto checkBeforeUpdate(UpdateHolidayWorkCommand command){
//		return checkBeforeRegisterHolidayWork.checkBeforeUpdate(command);
//	}
//	@POST
//	@Path("update")
//	public ProcessResult updateHolidayWork(UpdateHolidayWorkCommand command){
//		return updateHolidayWorkCommandHandle.handle(command);
//	}
//	@POST
//	@Path("getRecordWork")
//	public RecordWorkDto getRecordWork(RecordWorkParamHoliday param) {
//		/*AppHolidayWorkDto appHolidayWorkDto = (AppHolidayWorkDto) session.getAttribute("appHolidayWorkDto");*/
//		return this.appHolidayWorkFinder.getRecordWork(param.employeeID, param.appDate, param.siftCD,param.prePostAtr,param.getBreakTimeHours(), param.getWorkTypeCD(), 
//				param.getAppID(), param.getAppHdWorkDispInfoCmd());
//	}
//	
//	@POST
//	@Path("getBreakTimes")
//	public List<DeductionTimeDto> getBreakTimes(GetBreakTimeParam param) {
//		Optional<TimeWithDayAttr> opStartTime = param.getStartTime()==null ? Optional.empty() : Optional.of(new TimeWithDayAttr(param.getStartTime())); 
//		Optional<TimeWithDayAttr> opEndTime = param.getEndTime()==null ? Optional.empty() : Optional.of(new TimeWithDayAttr(param.getEndTime()));
//		return this.appHolidayWorkFinder.getBreakTimes(param.getWorkTypeCD(), param.getWorkTimeCD(), opStartTime, opEndTime);
//	}
//	
//	@POST
//	@Path("confirmInconsistency")
//	public List<String> confirmInconsistency(CreateHolidayWorkCommand command) {
//		String companyID = AppContexts.user().companyId();
//		Optional<OvertimeRestAppCommonSetting>  overTimeSettingOpt = overTimeSetRepo.getOvertimeRestAppCommonSetting(companyID, ApplicationType.HOLIDAY_WORK_APPLICATION.value);
//		List<ConfirmMsgOutput> outputLst = commonOvertimeHoliday.inconsistencyCheck(
//				companyID, 
//				command.getApplicantSID(), 
//				command.getApplicationDate(),
//				ApplicationType.HOLIDAY_WORK_APPLICATION,
//				overTimeSettingOpt.get().getAppDateContradictionAtr());
//		List<String> result = new ArrayList<>();
//		if(!CollectionUtil.isEmpty(outputLst)) {
//			result.add(outputLst.get(0).getMsgID());
//			for(String param : outputLst.get(0).getParamLst()) {
//				result.add(param);
//			}
//		}
//		return result; 
//	}
//	
}

@Value
class GetBreakTimeParam {
	String workTypeCD;
	String workTimeCD;
	Integer startTime;
	Integer endTime;
}
