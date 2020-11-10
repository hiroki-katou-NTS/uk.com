package nts.uk.ctx.at.request.ws.application.overtime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.app.command.application.holidaywork.CreateHolidayWorkCommand;
import nts.uk.ctx.at.request.app.command.application.overtime.CheckBeforeRegisterOvertime;
import nts.uk.ctx.at.request.app.command.application.overtime.CheckConvertPrePost;
import nts.uk.ctx.at.request.app.command.application.overtime.CreateOvertimeCommand;
import nts.uk.ctx.at.request.app.command.application.overtime.CreateOvertimeCommandHandler;
import nts.uk.ctx.at.request.app.command.application.overtime.UpdateOvertimeCommand;
import nts.uk.ctx.at.request.app.command.application.overtime.UpdateOvertimeCommandHandler;
import nts.uk.ctx.at.request.app.find.application.overtime.AppOvertimeFinder_Old;
import nts.uk.ctx.at.request.app.find.application.overtime.ParamGetOvertime;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.OverTimeDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.OvertimeCheckResultDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.ParamCaculationOvertime;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.ParamCalculateOvertime;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.ParamChangeAppDate;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.RecordWorkDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.RecordWorkParam;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.CommonOvertimeHoliday;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.PreActualColorResult;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.ConfirmMsgOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.ColorConfirmResult;
import nts.uk.ctx.at.request.dom.application.overtime.service.CaculationTime;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetting;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.DeductionTimeDto;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Path("at/request/application/overtime")
@Produces("application/json")
public class OvertimeWebService_Old extends WebService{

	@Inject
	private AppOvertimeFinder_Old overtimeFinder;
	@Inject
	private CreateOvertimeCommandHandler createHandler;
	@Inject
	private CheckBeforeRegisterOvertime checkBefore;
	@Inject
	private CheckConvertPrePost checkConvertPrePost;
	
	@Inject
	private UpdateOvertimeCommandHandler updateOvertimeCommandHandler;
	
	@Inject
	private OvertimeRestAppCommonSetRepository overTimeSetRepo;
	
	@Inject
	private CommonOvertimeHoliday commonOvertimeHoliday;
	
	@POST
	@Path("getOvertimeByUI")
	public OverTimeDto getOvertimeByUIType(ParamGetOvertime param) {
		return this.overtimeFinder.getOvertimeByUIType(param.getUrl(),
				param.getAppDate(),
				param.getUiType(),
				param.getTimeStart1(),
				param.getTimeEnd1(),
				param.getReasonContent(),
				param.getEmployeeIDs(),
				param.getEmployeeID());
	}
	
	@POST
	@Path("findByChangeAppDate")
	public OverTimeDto findByChangeAppDate(ParamChangeAppDate param) {
		return this.overtimeFinder.findByChangeAppDate(param.getAppDate(), 
				param.getPrePostAtr(),
				param.getSiftCD(),
				param.getOvertimeHours(),
				param.getWorkTypeCode(),
				param.getStartTime(),
				param.getEndTime(),
				param.getStartTimeRests(),
				param.getEndTimeRests(),
				param.getOvertimeAtr(),
				param.getChangeEmployee());
	}
	@POST
	@Path("checkConvertPrePost")
	public OverTimeDto convertPrePost(ParamChangeAppDate param) {
		return this.checkConvertPrePost.convertPrePost(
				param.getPrePostAtr(),
				param.getAppDate(),
				param.getSiftCD(),
				param.getOvertimeHours(),
				param.getWorkTypeCode(),
				param.getStartTime(),
				param.getEndTime(),
				param.getStartTimeRests(),
				param.getEndTimeRests(),
				param.getOvertimeSettingDataDto(),
				param.getOpAppBefore(), 
				param.isBeforeAppStatus(), 
				param.getActualStatus(), 
				param.getActualLst());
	}
	
	@POST
	@Path("calculationresultConfirm")
	public ColorConfirmResult calculationresultConfirm(ParamCaculationOvertime param){
		return this.overtimeFinder.calculationresultConfirm(param.getOvertimeHours(),param.getBonusTimes(),param.getPrePostAtr(), param.getAppDate(),param.getSiftCD(),param.getWorkTypeCode(),
				param.getStartTime(),
				param.getEndTime(),
				param.getStartTimeRests(),
				param.getEndTimeRests());
	}
	
	@POST
	@Path("getCaculationResult")
	public List<CaculationTime> getCaculationResult(ParamCaculationOvertime param) {
		return this.overtimeFinder.getCaculationValue(param.getOvertimeHours(),param.getBonusTimes(),param.getPrePostAtr(), param.getAppDate(),param.getSiftCD(),param.getWorkTypeCode(),
				param.getStartTime(),
				param.getEndTime(),
				param.getStartTimeRests(),
				param.getEndTimeRests());
	}
	
	@POST
	@Path("getCalculateValue")
	public PreActualColorResult getCalculateValue(ParamCalculateOvertime param) {
		return overtimeFinder.getCalculateValue(
				param.employeeID, 
				param.appDate, 
				param.prePostAtr, 
				param.workTypeCD, 
				param.workTimeCD, 
				param.overtimeInputLst, 
				param.startTime, 
				param.endTime, 
				param.getStartTimeRests(), 
				param.getEndTimeRests(),
				param.opAppBefore,
				param.beforeAppStatus,
				param.actualStatus,
				param.actualLst,
				param.overtimeSettingDataDto);
	}
	
	@POST
	@Path("getCalculationResultMob")
	public PreActualColorResult getCalculationResultMob(ParamCaculationOvertime param){
		return this.overtimeFinder.getCalculationResultMob(param.getOvertimeHours(),param.getBonusTimes(),param.getPrePostAtr(), param.getAppDate(),param.getSiftCD(),param.getWorkTypeCode(),
				param.getStartTime(),
				param.getEndTime(),
				param.getStartTimeRests(),
				param.getEndTimeRests(),
				param.isDisplayCaculationTime(),
				param.isFromStepOne(),
				param.opAppBefore,
				param.beforeAppStatus,
				param.actualStatus,
				param.actualLst,
				param.overtimeSettingDataDto);
	}
	
	@POST
	@Path("create")
	public ProcessResult createOvertime(CreateOvertimeCommand command){
		return createHandler.handle(command); 
	}
	
	@POST
	@Path("beforeRegisterColorConfirm")
	public ColorConfirmResult beforeRegisterColorConfirm(CreateOvertimeCommand command){
		return checkBefore.checkBeforeRegisterColor(command);
	}
	
	@POST
	@Path("checkBeforeRegister")
	public OvertimeCheckResultDto checkBeforeRegister(CreateOvertimeCommand command){
		return checkBefore.CheckBeforeRegister(command);
	}
	
	@POST
	@Path("checkBeforeUpdate")
	public OvertimeCheckResultDto checkBeforeUpdate(CreateOvertimeCommand command){
		return checkBefore.checkBeforeUpdate(command);
	}
	
	@POST
	@Path("findByAppID")
	public OverTimeDto findByChangeAppDate(String appID) {
		return this.overtimeFinder.findDetailByAppID(appID);
	}
	
	@POST
	@Path("update")
	public ProcessResult update(UpdateOvertimeCommand command) {
		return this.updateOvertimeCommandHandler.handle(command);
	}
	
	@POST
	@Path("getRecordWork")
	public RecordWorkDto getRecordWork(RecordWorkParam param) {
		return this.overtimeFinder.getRecordWork(param.employeeID, param.appDate, param.siftCD,param.prePostAtr,param.getOvertimeHours(),param.getWorkTypeCode(),
				param.getStartTimeRests(),
				param.getEndTimeRests(),
				param.isRestTimeDisFlg(),
				param.getOvertimeSettingDataDto());
	}
	
	@POST
	@Path("confirmInconsistency")
	public List<String> confirmInconsistency(CreateHolidayWorkCommand command) {
		String companyID = AppContexts.user().companyId();
		Optional<OvertimeRestAppCommonSetting>  overTimeSettingOpt = overTimeSetRepo.getOvertimeRestAppCommonSetting(companyID, ApplicationType.OVER_TIME_APPLICATION.value);
		List<ConfirmMsgOutput> outputLst = commonOvertimeHoliday.inconsistencyCheck(
				companyID, 
				command.getApplicantSID(), 
				command.getApplicationDate(),
				ApplicationType.OVER_TIME_APPLICATION,
				overTimeSettingOpt.get().getAppDateContradictionAtr());
		List<String> result = new ArrayList<>();
		if(!CollectionUtil.isEmpty(outputLst)) {
			result.add(outputLst.get(0).getMsgID());
			for(String param : outputLst.get(0).getParamLst()) {
				result.add(param);
			}
		}
		return result; 
	}
	
	@POST
	@Path("getByChangeTime")
	public List<DeductionTimeDto> getByChangeTime(ChangeTimeParam param) {
		String companyID = AppContexts.user().companyId();
		Optional<TimeWithDayAttr> opStartTime = param.startTime==null ? Optional.empty() : Optional.of(new TimeWithDayAttr(param.startTime)); 
		Optional<TimeWithDayAttr> opEndTime = param.endTime==null ? Optional.empty() : Optional.of(new TimeWithDayAttr(param.endTime));
		List<DeductionTime> breakTimes = this.commonOvertimeHoliday.getBreakTimes(companyID, param.workTypeCD, param.workTimeCD, opStartTime, opEndTime);
		List<DeductionTimeDto> timeZones = breakTimes.stream().map(domain->{
			DeductionTimeDto dto = new DeductionTimeDto();
			domain.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());
		return timeZones;
	}
}
