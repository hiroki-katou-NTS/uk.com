package nts.uk.ctx.at.function.dom.alarm.alarmlist.aggregationprocess.agreementprocess;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.adapter.agreement.CheckRecordAgreementAdapter;
import nts.uk.ctx.at.function.dom.adapter.agreement.CheckedAgreementImport;
import nts.uk.ctx.at.function.dom.adapter.agreement.CheckedOvertimeImport;
import nts.uk.ctx.at.function.dom.alarm.AlarmCategory;
import nts.uk.ctx.at.function.dom.alarm.alarmdata.ValueExtractAlarm;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.EmployeeSearchDto;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionByCategory;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionByCategoryRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.AgreeCondOt;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.AgreeConditionError;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.AgreeNameError;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.AlarmChkCondAgree36;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.IAgreeNameErrorRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.UseClassification;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class AgreementProcessService {
	
	@Inject
	private AlarmCheckConditionByCategoryRepository alarmCheckRepo;
	
	@Inject
	private CheckRecordAgreementAdapter checkAgreementAdapter;
	
	@Inject
	private IAgreeNameErrorRepository agreeNameRepo;
	
	public List<ValueExtractAlarm> agreementProcess(List<String> checkConditionCodes, List<DatePeriod> periods, List<EmployeeSearchDto> employees){
		
		List<ValueExtractAlarm> result = new ArrayList<ValueExtractAlarm>();
		
		List<String> employeeIds = employees.stream().map( e ->e.getId()).collect(Collectors.toList());
		
		// get List<カテゴリ別アラームチェック条件>
		List<AlarmCheckConditionByCategory> listAlarmCheck= alarmCheckRepo.findByCategoryAndCode(AppContexts.user().companyId()	, AlarmCategory.AGREEMENT.value	, checkConditionCodes);
		
		Map<String, EmployeeSearchDto> mapEmployee = employees.stream().collect(Collectors.toMap(EmployeeSearchDto::getId, x ->x));
		
		for(AlarmCheckConditionByCategory alarmCheck : listAlarmCheck) {
			// 36協定のアラームチェック条件
			AlarmChkCondAgree36 alarmChkCon36 = alarmCheck.getAlarmChkCondAgree36();
			
			// List<36協定エラーアラームのチェック条件>
			List<AgreeConditionError> listCondErrorAlarm  = alarmChkCon36.getListCondError().stream().filter( e ->e.getUseAtr()==UseClassification.Use).collect(Collectors.toList());

			List<CheckedAgreementImport> checkAgreements = checkAgreementAdapter.checkArgreement(employeeIds, periods, listCondErrorAlarm);

			for(CheckedAgreementImport check:  checkAgreements) {
					
					Optional<AgreeNameError> optAgreeName = agreeNameRepo.findById(check.getPeriod().value, check.getErrorAlarm().value);
					if(!optAgreeName.isPresent()) throw new RuntimeException("Not found name of 36 agreement!");
					String agreeName =  optAgreeName.get().getName().v();
					
					String datePeriod = check.getDatePeriod().start().toString() + "~" + check.getDatePeriod().end().toString();
					
					result.add(new ValueExtractAlarm(mapEmployee.get(check.getEmployeeId()).getWorkplaceId(), check.getEmployeeId(), datePeriod, 
							TextResource.localize("KAL010_208"), agreeName, TextResource.localize("KAL010_203", agreeName), check.getMessageDisp().v()));

			}
			
						
			
			// List<36協定時間超過回数のチェック条件>
			List<AgreeCondOt> listCondOt = alarmChkCon36.getListCondOt();
			List<CheckedOvertimeImport> checkOvertimes = checkAgreementAdapter.checkNumberOvertime(employeeIds, periods, listCondOt);
			
			for(CheckedOvertimeImport  check : checkOvertimes) {
					
					String hour = check.getOt36().hour() +"";
					if(hour.length()<2) hour = "0" + hour;
					String minute = check.getOt36().minute() +"";
					if(minute.length()<2) minute ="0" + minute;
					String ot36 =hour +":" + minute ;
					
					String datePeriod = check.getDatePeriod().start().toString() + "~" + check.getDatePeriod().end().toString();
					
					result.add(new ValueExtractAlarm(mapEmployee.get(check.getEmployeeId()).getWorkplaceId(), check.getEmployeeId(), datePeriod, TextResource.localize("KAL010_208"),
							TextResource.localize("KAL010_201"), TextResource.localize("KAL010_202", check.getNo()+"", ot36, check.getExcessNum().v()+"" ), check.getMessageDisp().v()));
			}
			
			
		}
		
		
		return result;
	}
}
