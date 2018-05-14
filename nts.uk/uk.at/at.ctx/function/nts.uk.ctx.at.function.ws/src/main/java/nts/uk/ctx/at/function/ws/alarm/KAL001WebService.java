package nts.uk.ctx.at.function.ws.alarm;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.command.JavaTypeResult;
import nts.uk.ctx.at.function.app.command.alarm.extraprocessstatus.ActiveAlarmListExtraProcessCommand;
import nts.uk.ctx.at.function.app.command.alarm.extraprocessstatus.FinishAlarmListExtraProcessHandler;
import nts.uk.ctx.at.function.app.command.alarm.extraprocessstatus.StartAlarmListExtraProcessHandler;
import nts.uk.ctx.at.function.app.find.alarm.AlarmPatternSettingFinder;
import nts.uk.ctx.at.function.app.find.alarm.CheckConditionTimeFinder;
import nts.uk.ctx.at.function.app.find.alarm.CodeNameAlarmDto;
import nts.uk.ctx.at.function.app.find.alarm.alarmlist.EmployeeInfoFunFinder;
import nts.uk.ctx.at.function.app.find.alarm.alarmlist.EmployeeInfoInput;
import nts.uk.ctx.at.function.app.find.alarm.alarmlist.EmployeeSendEmail;
import nts.uk.ctx.at.function.app.find.alarm.alarmlist.ExtractAlarmListFinder;
import nts.uk.ctx.at.function.app.find.alarm.alarmlist.ExtractAlarmQuery;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.ExtractedAlarmDto;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.CheckConditionTimeDto;
import nts.uk.ctx.at.function.dom.alarm.extraprocessstatus.AlarmListExtraProcessStatusRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author dxthuong
 *
 * Web Service for function KAL001_アラームリスト
 */
@Path("at/function/alarm/kal/001")
@Produces("application/json")
public class KAL001WebService {

	@Inject
	private AlarmPatternSettingFinder alarmFinder;
	
	@Inject
	private CheckConditionTimeFinder checkConditionFinder;
	
	@Inject
	private ExtractAlarmListFinder extractAlarmFinder;
	
	@Inject
	private EmployeeInfoFunFinder employeeInfoFunFinder;
	
	@Inject
	private AlarmListExtraProcessStatusRepository alListExtraProcessStatusRepo;
	
	@Inject
	private StartAlarmListExtraProcessHandler startExtractHandler;
	
	@Inject
	private FinishAlarmListExtraProcessHandler finishExtractHandler;
	
	@POST
	@Path("pattern/setting")
	public List<CodeNameAlarmDto> getAlarmByUser(){
		return alarmFinder.getCodeNameAlarm();
	}
	
	@POST
	@Path("check/condition/time")
	public List<CheckConditionTimeDto> getCheckConditionTime(String alarmCode){
		return checkConditionFinder.getCheckConditionTime(alarmCode);
	}
	
	@POST
	@Path("extract/alarm")
	public ExtractedAlarmDto extractAlarm(ExtractAlarmQuery query) {
		return extractAlarmFinder.extractAlarm(query);
	}
	
	@POST
	@Path("isextracting")
	public JavaTypeResult<Boolean> isExtracting() {
		String companyID = AppContexts.user().companyId();
		String employeeId = AppContexts.user().employeeId();
		return new JavaTypeResult<Boolean>(alListExtraProcessStatusRepo.isAlListExtaProcessing(companyID, employeeId));
	}
	
	@POST
	@Path("extractstarting")
	public JavaTypeResult<String> extractStarting() {
		return new JavaTypeResult<String>(startExtractHandler.handle(new ActiveAlarmListExtraProcessCommand()));
	}
	
	@POST
	@Path("extractfinished")
	public void extractFinished(ActiveAlarmListExtraProcessCommand command) {
		finishExtractHandler.handle(command);
	}
	
	@POST
	@Path("get/employee/sendEmail")
	public List<EmployeeSendEmail> extractAlarm(List<EmployeeInfoInput> listEmployeeSendEmail) {
		return employeeInfoFunFinder.getListEmployee(listEmployeeSendEmail);
	}
}
