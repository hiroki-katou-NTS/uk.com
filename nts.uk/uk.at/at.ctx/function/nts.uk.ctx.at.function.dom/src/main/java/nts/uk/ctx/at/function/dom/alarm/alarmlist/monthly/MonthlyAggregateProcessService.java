package nts.uk.ctx.at.function.dom.alarm.alarmlist.monthly;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.ExtraResultMonthlyFunAdapter;
import nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.FixedExtraMonFunAdapter;
import nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.FixedExtraMonFunImport;
import nts.uk.ctx.at.function.dom.alarm.AlarmCategory;
import nts.uk.ctx.at.function.dom.alarm.alarmdata.ValueExtractAlarm;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.EmployeeSearchDto;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionByCategoryRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.daily.DailyAlarmCondition;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.monthly.MonAlarmCheckCon;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.monthly.dtoevent.ExtraResultMonthlyDomainEventDto;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.PublicHolidayMonthSetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.Year;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.company.CompanyMonthDaySetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.company.CompanyMonthDaySettingRepository;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidaySetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidaySettingRepository;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.employee.EmployeeMonthDaySetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.employee.EmployeeMonthDaySettingRepository;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.employment.EmploymentMonthDaySetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.employment.EmploymentMonthDaySettingRepository;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.workplace.WorkplaceMonthDaySetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.workplace.WorkplaceMonthDaySettingRepository;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

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
	
	public List<ValueExtractAlarm> monthlyAggregateProcess(String companyID , String  checkConditionCode,DatePeriod period,List<EmployeeSearchDto> employees){
		
		List<String> employeeIds = employees.stream().map( e ->e.getId()).collect(Collectors.toList());
		
		List<ValueExtractAlarm> listValueExtractAlarm = new ArrayList<>(); 	
		
		alCheckConByCategoryRepo.find(companyID, AlarmCategory.MONTHLY.value, checkConditionCode).ifPresent(alCheckConByCategory -> {
			MonAlarmCheckCon monAlarmCheckCon = (MonAlarmCheckCon) alCheckConByCategory.getExtractionCondition();
			//tab 2
			List<FixedExtraMonFunImport> listFixed = fixedExtraMonFunAdapter.getByEralCheckID(monAlarmCheckCon.getMonAlarmCheckConID());
			this.extractMonthlyFixed(listFixed, period, employees);
			//tab 3
			List<ExtraResultMonthlyDomainEventDto> listExtra = extraResultMonthlyFunAdapter.getListExtraResultMonByListEralID(monAlarmCheckCon.getArbExtraCon());
			this.extraResultMonthly(companyID, listExtra, period, employees);
			
		});
		
		
		//対象者を絞り込む
		List<String> listEmployeeID = new ArrayList<>();
		
		//対象者の件数をチェック : 対象者　≦　0
		if(listEmployeeID.isEmpty()) {
			return Collections.emptyList();
		}
		
		return listValueExtractAlarm;
	}
	//tab 2
	private List<ValueExtractAlarm> extractMonthlyFixed(List<FixedExtraMonFunImport> listFixed,
			DatePeriod period, List<EmployeeSearchDto> employee) {
		List<ValueExtractAlarm> listValueExtractAlarm = new ArrayList<>(); 
		for(int i = 0;i<listFixed.size();i++) {
			if(listFixed.get(i).isUseAtr()) {
				FixedExtraMonFunImport fixedExtraMon = listFixed.get(i);
				switch(i) {
					case 0 :break;
					case 1 :break;
					case 2 :break;
					case 3 :break;
					case 4 :break;
					default :break;
				}//end switch
				
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
						this.checkPublicHoliday(companyId,employee.getWorkplaceCode(), employee.getId(),employee.getWorkplaceId(), true, yearMonth);
					}
				}
			}
			
			return listValueExtractAlarm;
		}
	
		
		//公休所定日数をチェックする
		private boolean checkPublicHoliday(String companyId, String employeeCd , String employeeId,String workplaceId ,boolean isManageComPublicHd, YearMonth yearMonth ){
			Year y =new Year(yearMonth.year());
			CompanyId c = new CompanyId(companyId);
			//公休設定
			Optional<PublicHolidaySetting> optPublicHolidaySetting = this.publicHolidaySettingRepo.findByCID(companyId);
			if(optPublicHolidaySetting.isPresent()){
				PublicHolidaySetting publicHolidaySetting = optPublicHolidaySetting.get();
				if(isManageComPublicHd!=publicHolidaySetting.isManageComPublicHd()){
					return false;
				}
				//ドメインモデル「社員月間日数設定」を取得する
				Optional<EmployeeMonthDaySetting> optEmployeeMonthDaySetting = this.employeeMonthDaySettingRepo.findByYear(c, employeeId, y);
				
				//ドメインモデル「職場月間日数設定」を取得する
				Optional<WorkplaceMonthDaySetting> optWorkplaceMonthDaySetting = this.workplaceMonthDaySettingRepo.findByYear(c, workplaceId, y);
				
				//ドメインモデル「雇用月間日数設定」を取得する
				Optional<EmploymentMonthDaySetting> optEmploymentMonthDaySetting = this.employmentMonthDaySettingRepo.findByYear(c, employeeCd, y);
				
				//ドメインモデル「会社月間日数設定」を取得する
				Optional<CompanyMonthDaySetting> optCompanyMonthDaySetting = this.companyMonthDaySettingRepo.findByYear(c, y);
				
				List<PublicHolidayMonthSetting> publicHolidayMonthSettings=null;
				if(optEmployeeMonthDaySetting.isPresent()&&optEmployeeMonthDaySetting.get().getPublicHolidayMonthSettings()!=null && !optEmployeeMonthDaySetting.get().getPublicHolidayMonthSettings().isEmpty()){
					 publicHolidayMonthSettings = optEmployeeMonthDaySetting.get().getPublicHolidayMonthSettings();
				}
				else if(optWorkplaceMonthDaySetting.isPresent() &&optWorkplaceMonthDaySetting.get().getPublicHolidayMonthSettings()!=null && !optWorkplaceMonthDaySetting.get().getPublicHolidayMonthSettings().isEmpty()){
					 publicHolidayMonthSettings = optWorkplaceMonthDaySetting.get().getPublicHolidayMonthSettings();
				}
				else if(optEmploymentMonthDaySetting.isPresent() && optEmploymentMonthDaySetting.get().getPublicHolidayMonthSettings()!=null && !optEmploymentMonthDaySetting.get().getPublicHolidayMonthSettings().isEmpty()){
					 publicHolidayMonthSettings = optEmploymentMonthDaySetting.get().getPublicHolidayMonthSettings();
				}
				else if(optCompanyMonthDaySetting.isPresent()&& optCompanyMonthDaySetting.get().getPublicHolidayMonthSettings()!=null && !optCompanyMonthDaySetting.get().getPublicHolidayMonthSettings().isEmpty()){
					 publicHolidayMonthSettings = optCompanyMonthDaySetting.get().getPublicHolidayMonthSettings();
				}
				if(publicHolidayMonthSettings==null){
					return false;
				}
				
				
			}
			return false;
		}
		
		private void check36AgreementCondition(){
			
		}
		

}
