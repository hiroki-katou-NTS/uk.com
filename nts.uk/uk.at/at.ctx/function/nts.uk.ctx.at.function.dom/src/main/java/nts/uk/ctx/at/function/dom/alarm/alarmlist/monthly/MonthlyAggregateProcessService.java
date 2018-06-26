package nts.uk.ctx.at.function.dom.alarm.alarmlist.monthly;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.function.dom.adapter.ResponseImprovementAdapter;
import nts.uk.ctx.at.function.dom.adapter.checkresultmonthly.CheckResultMonthlyAdapter;
import nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.ExtraResultMonthlyFunAdapter;
import nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.FixedExtraMonFunAdapter;
import nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.FixedExtraMonFunImport;
import nts.uk.ctx.at.function.dom.adapter.sysfixedcheckcondition.SysFixedCheckConMonAdapter;
import nts.uk.ctx.at.function.dom.alarm.AlarmCategory;
import nts.uk.ctx.at.function.dom.alarm.alarmdata.ValueExtractAlarm;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.EmployeeSearchDto;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionByCategory;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionByCategoryRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.monthly.MonAlarmCheckCon;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.monthly.dtoevent.ExtraResultMonthlyDomainEventDto;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.company.CompanyMonthDaySettingRepository;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidaySettingRepository;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.employee.EmployeeMonthDaySettingRepository;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.employment.EmploymentMonthDaySettingRepository;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.workplace.WorkplaceMonthDaySettingRepository;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
/**
 * 月次の集計処理
 * @author tutk
 *
 */
@Stateless
public class MonthlyAggregateProcessService {
	
	@Inject
	private AlarmCheckConditionByCategoryRepository alCheckConByCategoryRepo;
	
	@Inject
	private ExtraResultMonthlyFunAdapter extraResultMonthlyFunAdapter;
	
	@Inject
	private FixedExtraMonFunAdapter fixedExtraMonFunAdapter;
	
	@Inject
	private PublicHolidaySettingRepository publicHolidaySettingRepo;
	
	@Inject 
	private EmployeeMonthDaySettingRepository employeeMonthDaySettingRepo;
	
	@Inject
	private WorkplaceMonthDaySettingRepository workplaceMonthDaySettingRepo;
	
	@Inject
	private EmploymentMonthDaySettingRepository employmentMonthDaySettingRepo;
	
	@Inject
	private CompanyMonthDaySettingRepository companyMonthDaySettingRepo;
	
	@Inject
	private ResponseImprovementAdapter responseImprovementAdapter;
	
	@Inject 
	private SysFixedCheckConMonAdapter sysFixedCheckConMonAdapter;
	
	@Inject
	private CheckResultMonthlyAdapter checkResultMonthlyAdapter;
	
	public List<ValueExtractAlarm> monthlyAggregateProcess(String companyID , String  checkConditionCode,DatePeriod period,List<EmployeeSearchDto> employees){
		
		
		
		List<String> employeeIds = employees.stream().map( e ->e.getId()).collect(Collectors.toList());
		
		List<ValueExtractAlarm> listValueExtractAlarm = new ArrayList<>(); 	
		
		
		Optional<AlarmCheckConditionByCategory> alCheckConByCategory =   alCheckConByCategoryRepo.find(companyID, AlarmCategory.MONTHLY.value, checkConditionCode);
		if(!alCheckConByCategory.isPresent()) {
			return Collections.emptyList();
		}
				
		MonAlarmCheckCon monAlarmCheckCon = (MonAlarmCheckCon) alCheckConByCategory.get().getExtractionCondition();
		List<FixedExtraMonFunImport> listFixed = fixedExtraMonFunAdapter.getByEralCheckID(monAlarmCheckCon.getMonAlarmCheckConID());
		List<ExtraResultMonthlyDomainEventDto> listExtra = extraResultMonthlyFunAdapter.getListExtraResultMonByListEralID(monAlarmCheckCon.getArbExtraCon());
		
		//対象者を絞り込む
		DatePeriod endDatePerior = new DatePeriod(period.end(),period.end());
		List<String> listEmployeeID = responseImprovementAdapter.reduceTargetResponseImprovement(employeeIds, endDatePerior, alCheckConByCategory.get().getExtractTargetCondition());
		
		//対象者の件数をチェック : 対象者　≦　0
		if(listEmployeeID.isEmpty()) {
			return Collections.emptyList();
		}
		//tab 2
		listValueExtractAlarm.addAll(this.extractMonthlyFixed(listFixed, period, listEmployeeID));
		//tab 3
		List<EmployeeSearchDto> employeesDto = employees.stream().filter(c-> listEmployeeID.contains(c.getId())).collect(Collectors.toList());
		listValueExtractAlarm.addAll(this.extraResultMonthly(companyID, listExtra, period, employeesDto));
		
		return listValueExtractAlarm;
	}
	//tab 2
	private List<ValueExtractAlarm> extractMonthlyFixed(List<FixedExtraMonFunImport> listFixed,
			DatePeriod period, List<String> employees) {
		List<ValueExtractAlarm> listValueExtractAlarm = new ArrayList<>();
		List<YearMonth> lstYearMonth = period.yearMonthsBetween();
		
		for(String employee : employees) {
			for (YearMonth yearMonth : lstYearMonth) {
				for(int i = 0;i<listFixed.size();i++) {
					if(listFixed.get(i).isUseAtr()) {
//						FixedExtraMonFunImport fixedExtraMon = listFixed.get(i);
						switch(i) {
							case 0 :
								Optional<ValueExtractAlarm> unconfirmed = sysFixedCheckConMonAdapter.checkMonthlyUnconfirmed(employee, yearMonth.v().intValue());
								if(unconfirmed.isPresent()) {
									listValueExtractAlarm.add(unconfirmed.get());
								}
								
							break;
							case 1 :break;//chua co
							case 2 :break;//chua co
							case 3 :break;//chua co
							case 4 :
								Optional<ValueExtractAlarm> agreement = sysFixedCheckConMonAdapter.checkAgreement(employee, yearMonth.v().intValue());
								if(agreement.isPresent()) {
									listValueExtractAlarm.add(agreement.get());
								}
							break;
							default : break; // so 6 : chua co
						}//end switch
						
					}
						
				}
			}
			
		}
		
		
		return listValueExtractAlarm;
	}
	
	
	//tab 3
	private List<ValueExtractAlarm> extraResultMonthly(String companyId,List<ExtraResultMonthlyDomainEventDto> listExtra,
			DatePeriod period, List<EmployeeSearchDto> employees) {
		List<ValueExtractAlarm> listValueExtractAlarm = new ArrayList<>(); 
		List<YearMonth> lstYearMonth = period.yearMonthsBetween();
		for (ExtraResultMonthlyDomainEventDto extra : listExtra) {
			for (YearMonth yearMonth : lstYearMonth) {
				for (EmployeeSearchDto employee : employees) {
					switch (extra.getTypeCheckItem()) {
					case 0:
						checkResultMonthlyAdapter.checkPublicHoliday(companyId, employee.getCode(), employee.getId(),
								employee.getWorkplaceId(), true, yearMonth, extra.getSpecHolidayCheckCon());
						break;
					case 1:case 2:
//						checkResultMonthlyAdapter.check36AgreementCondition(companyId, employee.getId(), date, 
//								yearMonth, yearMonth.year(),extra.getAgreementCheckCon36());
						break;
					case 4 : 
//						checkResultMonthlyAdapter.checkPublicHoliday(companyId, employee.getCode(), employee.getId(),
//								employee.getWorkplaceId(), true, yearMonth, extra.getSpecHolidayCheckCon())
						break;
					default:
						break;
					}
					
					//this.checkPublicHoliday(companyId,employee.getWorkplaceCode(), employee.getId(),employee.getWorkplaceId(), true, yearMonth);
				}
			}
		}
		
		return listValueExtractAlarm;
	}
		

}
