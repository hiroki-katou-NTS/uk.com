package nts.uk.ctx.at.function.ac.agreement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.function.dom.adapter.agreement.CheckRecordAgreementAdapter;
import nts.uk.ctx.at.function.dom.adapter.agreement.CheckedAgreementImport;
import nts.uk.ctx.at.function.dom.adapter.agreement.CheckedOvertimeImport;
import nts.uk.ctx.at.function.dom.adapter.employment.EmploymentAdapter;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.AgreeCondOt;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.AgreeConditionError;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.ErrorAlarm;
import nts.uk.ctx.at.record.pub.monthly.agreement.AgreementTimeOfManagePeriodPub;
import nts.uk.ctx.at.record.pub.monthlyprocess.agreement.AgreementTimeExport;
import nts.uk.ctx.at.record.pub.monthlyprocess.agreement.GetAgreementTimePub;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreementTimeStatusOfMonthly;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;
@Stateless
public class CheckRecordAgreementAcAdapter implements CheckRecordAgreementAdapter {

	@Inject
	private EmploymentAdapter employmentAdapter;

	@Inject
	private ClosureEmploymentRepository closureEmpRepo;
	
	@Inject
	private GetAgreementTimePub agreementTimePub;
	
	@Inject
	private AgreementTimeOfManagePeriodPub agreementTimeOfManagePub;
	
	@Override
	public List<CheckedAgreementImport> checkArgreement(List<String> employeeIds, List<DatePeriod> periods,
			List<AgreeConditionError> listCondErrorAlarm) {
		
		List<CheckedAgreementImport> result = new ArrayList<CheckedAgreementImport>();
		
		if(listCondErrorAlarm.isEmpty() || employeeIds.isEmpty()) return result; 
				
		List<AgreeConditionError> listCondError = listCondErrorAlarm.stream().filter( e->e.getErrorAlarm()==ErrorAlarm.Error ).collect(Collectors.toList());
		List<AgreeConditionError> listCondAlarm = listCondErrorAlarm.stream().filter( e->e.getErrorAlarm()==ErrorAlarm.Alarm ).collect(Collectors.toList());
		
		
		for(DatePeriod period : periods) {
			String employmentCode = employmentAdapter.getClosure(AppContexts.user().employeeId(), period.end());

			Optional<ClosureEmployment> closureEmploymentOpt = closureEmpRepo
					.findByEmploymentCD(AppContexts.user().companyId(), employmentCode);
			if (!closureEmploymentOpt.isPresent())
				throw new RuntimeException(" Clousure not find!");
			if (closureEmploymentOpt.get().getClosureId() == null)
				throw new RuntimeException("Closure is null!");
			Integer closureId = closureEmploymentOpt.get().getClosureId();
			
			YearMonth yearMonth = period.end().yearMonth();
			
			List<AgreementTimeExport> agreementTimeExport = agreementTimePub
					.get(AppContexts.user().companyId(), employeeIds, yearMonth, ClosureId.valueOf(closureId));
			
			List<AgreementTimeExport> exportError = agreementTimeExport.stream()
					.filter(e -> e.getConfirmed().isPresent()
							&& (e.getConfirmed().get().getStatus() == AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ERROR
									|| e.getConfirmed().get()
											.getStatus() == AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ERROR))
					.collect(Collectors.toList());
			
			
			List<AgreementTimeExport> exportAlarm = agreementTimeExport.stream()
					.filter(e -> e.getConfirmed().isPresent()
							&& e.getConfirmed().get().getStatus() == AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ALARM)
					.collect(Collectors.toList());		
			
			
			for(AgreementTimeExport error : exportError) {
				for(AgreeConditionError condError: listCondError) {
					result.add(CheckedAgreementImport.builder().employeeId(error.getEmployeeId()).datePeriod(period)
							.alarmCheckId(condError.getId()).period(condError.getPeriod()).errorAlarm(ErrorAlarm.Error)
							.messageDisp(condError.getMessageDisp()).build());
				}				
			}
			
			for(AgreementTimeExport alarm : exportAlarm) {
				for(AgreeConditionError condAlarm : listCondAlarm) {
					result.add(CheckedAgreementImport.builder().employeeId(alarm.getEmployeeId()).datePeriod(period)
							.alarmCheckId(condAlarm.getId()).period(condAlarm.getPeriod()).errorAlarm(ErrorAlarm.Alarm)
							.messageDisp(condAlarm.getMessageDisp()).build());
				}
			}
		}
		

		
		return result;
	}

	@Override
	public List<CheckedOvertimeImport> checkNumberOvertime(List<String> employeeIds, List<DatePeriod> periods,
			List<AgreeCondOt> listCondOt) {
		
		List<CheckedOvertimeImport> result = new ArrayList<CheckedOvertimeImport>();
		for(DatePeriod  period : periods) {
			
			YearMonthPeriod  yearMonthPeriod = new YearMonthPeriod(period.start().yearMonth(), period.end().yearMonth());
			Map<String, Map<YearMonth, AttendanceTimeMonth>> agreementMultipEmp=  agreementTimeOfManagePub.getTimeByPeriod(employeeIds, yearMonthPeriod);
			
			List<String> employeeIdsError = new ArrayList<>(agreementMultipEmp.keySet());
			for(String employeeId : employeeIdsError) {

				List<AttendanceTimeMonth> agreementOneEmp = new ArrayList<>(agreementMultipEmp.get(employeeId).values());
				
				for(AgreeCondOt agreeCond: listCondOt ) {
					
					int count = 0;
					for(int i =0 ; i< agreementOneEmp.size(); i++) {
						if(agreementOneEmp.get(i).valueAsMinutes() >= agreeCond.getOt36().valueAsMinutes())
							count ++;						
				    }
					if(count >= agreeCond.getExcessNum().v()) {
						result.add(CheckedOvertimeImport.builder().employeeId(employeeId).datePeriod(period)
								.alarmCheckId(agreeCond.getId()).error(true).no(agreeCond.getNo())
								.ot36(agreeCond.getOt36()).excessNum(agreeCond.getExcessNum())
								.messageDisp(agreeCond.getMessageDisp()).build());
					}
				
			    }
			
		    } 

		}
		return result;
	}

}
